import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;

import com.ptc.cipjava.jxthrowable;
import com.ptc.pfc.pfcDrawing.Drawing;
import com.ptc.pfc.pfcModel.ModelType;
import com.ptc.pfc.pfcModel.pfcModel;
//import com.ptc.pfc.pfcGlobal.pfcGlobal;
import com.ptc.pfc.pfcModelItem.Parameter;
import com.ptc.pfc.pfcModelItem.pfcModelItem;
import com.ptc.pfc.pfcSession.Session;
import com.ptc.pfc.pfcSolid.RegenInstructions;
import com.ptc.pfc.pfcSolid.Solid;
import com.ptc.pfc.pfcSolid.pfcSolid;
import com.ptc.pfc.pfcView2D.View2D;
import com.ptc.pfc.pfcView2D.View2Ds;
import com.ptc.pfc.pfcView2D.pfcView2D;
import com.ptc.pfc.pfcWindow.DotsPerInch;
import com.ptc.pfc.pfcWindow.JPEGImageExportInstructions;
import com.ptc.pfc.pfcWindow.RasterDepth;
import com.ptc.pfc.pfcWindow.pfcWindow;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.ConnectionException;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.communications.client.things.filetransfer.FileTransferVirtualThing;
import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinitions;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.primitives.StringPrimitive;


@ThingworxEventDefinitions(events = {
		@ThingworxEventDefinition(name="ModelSaved", description="Creo model saved event", dataShape="CreoModelSavedEventData")
})

@SuppressWarnings("serial")
public class AsyncParameterizedModelThing extends FileTransferVirtualThing implements Runnable {
		public boolean busy = false;
	  //Session session;
	  //Solid solid;
	  String username;
	  int screenshotWidth,screenshotHeight;
	ValueCollection parameters;
	CreoParametersPlugin creo;
	ConnectedThingClient client;
	
	private Thread _shutdownThread = null;
	
	public AsyncParameterizedModelThing(CreoParametersPlugin creo, String name, String description, String identifier, ConnectedThingClient client, HashMap<String, String> virtualDirs) {
		super(name,description,client,virtualDirs);
		setIdentifier(identifier);
		//screenshotWidth = w;
		//screenshotHeight = h;
		// Data Shape definition that is used by the steam sensor fault event
		// The event only has one field, the message
		FieldDefinitionCollection faultFields = new FieldDefinitionCollection();
		faultFields.addFieldDefinition(new FieldDefinition(CommonPropertyNames.PROP_MESSAGE,BaseTypes.STRING));
		defineDataShapeDefinition("CreoModelSavedEventData", faultFields);
		this.client= client;
		this.creo = creo;
		screenshotWidth = 7;
		screenshotHeight = 7;
		parameters = new ValueCollection(); 
		parameters.put("sourceFile", new StringPrimitive("pic1.jpg"));
		parameters.put("sourcePath", new StringPrimitive("/logs"));
		parameters.put("sourceRepo", new StringPrimitive(client.getGatewayName()));
		parameters.put("targeFile", new StringPrimitive("pic1.jpg"));
		parameters.put("targetPath", new StringPrimitive("\\"));
		parameters.put("targetRepo", new StringPrimitive("SystemRepository"));
		
		// Populate the thing shape with the properties, services, and events that are annotated in this code
		super.initializeFromAnnotations();
	
	}

	// From the VirtualThing class
	// This method will get called when a connect or reconnect happens
	// Need to send the values when this happens
	// This is more important for a solution that does not send its properties on a regular basis
	public void synchronizeState() {
		// Be sure to call the base class
		super.synchronizeState();
		// Send the property values to Thingworx when a synchronization is required
		super.syncProperties();
	}
	
	@ThingworxServiceDefinition( name="generateModel", description="Generate a new model in creo")
	@ThingworxServiceResult( name=CommonPropertyNames.PROP_RESULT, description="", baseType="NOTHING")
	public void generateModel( 
			@ThingworxServiceParameter( name="name", description="name", baseType="STRING" ) String name,
			@ThingworxServiceParameter( name="parameters", description="parameters", baseType="STRING" ) String parameters
			) throws Exception {

		System.out.println("generate model "+ name);
		username = name;
		parameters = parameters.trim();
		int a=0,b=0,c=0,d=0;
		try {
			a = Integer.parseInt(parameters.substring(0, 1));
			b = Integer.parseInt(parameters.substring(1, 2));
			c = Integer.parseInt(parameters.substring(2, 3));
			d = Integer.parseInt(parameters.substring(3, 4));
		}
		catch (Exception e)
		{
			a=b=c=d=0;
		}

        while ( busy)
        {
        	System.out.println("waiting 1 sec...");
        	Thread.sleep(1000);
        }
        busy = true;
        if (creo.connection==null)
        	creo.connect();
/*
		System.out.println("connect done, check modifyable");
        if ( !creo.solid.CheckIsModifiable(false) )
        {

    		System.out.println("not modifyable regening");
        	creo.solid.Regenerate(null);
        }*/
		//System.out.println("cleanup dependencies");
        //creo.solid.CleanupDependencies();
		setParameters(name,a,b,c,d);
		//sendFile();		
	}

	@ThingworxServiceDefinition( name="Shutdown", description="Shutdown the client")
	@ThingworxServiceResult( name=CommonPropertyNames.PROP_RESULT, description="", baseType="NOTHING")
	public synchronized void Shutdown() throws Exception {
		// Should not have to do this, but guard against this method being called more than once.
		if(this._shutdownThread == null) {
			// Create a thread for shutting down and start the thread
			this._shutdownThread = new Thread(this);
			this._shutdownThread.start();
		}
	}
	
	@Override
	public void run() {
		try {
			// Delay for a period to verify that the Shutdown service will return
			Thread.sleep(1000);
			// Shutdown the client
			this.getClient().shutdown();
			creo.exitflag = true;
		} catch (Exception x) {
			// Not much can be done if there is an exception here
			// In the case of production code should at least log the error
		}
	}

	
	void setParameters(String name, int a, int b , int c , int d)
	{
		System.out.println("set params");
		username = name;
		try {	
		      //session = pfcGlobal.GetProESession();	    
		     // solid = (Solid) session.GetCurrentModel();
			Parameter p;
			p = creo.solid.GetParam("A");		      
			p.SetValue (pfcModelItem.CreateIntParamValue(a));				
			p = creo.solid.GetParam("B");
			p.SetValue (pfcModelItem.CreateIntParamValue(b));
			p = creo.solid.GetParam("C");
			p.SetValue (pfcModelItem.CreateIntParamValue(c));
			p = creo.solid.GetParam("D");
			p.SetValue (pfcModelItem.CreateIntParamValue(d));

			p = creo.solid.GetParam("NAME");
			p.SetValue (pfcModelItem.CreateStringParamValue(name));
			
			//RegenInstructions r = pfcSolid.RegenInstructions_Create(null,null,null);

			//creo.connection.EventProcess();
			System.out.println("regen");
			//r.SetRefreshModelTree(true);
			//r.SetForceRegen(true);
			creo.solid.Regenerate(null);
			
			/*pfcSheet.SheetOwner.SetSheetScale(null);
			if (creo.session.GetCurrentModel().GetType() == ModelType.MDL_DRAWING)
			{
				Drawing drawing = (Drawing) creo.session.GetCurrentModel();
				View2Ds views = drawing.List2DViews();
				for ( int i=0; i< views.getarraysize(); i++)
				{
					View2D v = views.get(i);
					System.out.println(v.GetName());
					System.out.println(v.GetScale());
					v.Regenerate();
				}
			}*/
		/*	pfcModel2D.Model2D.GetViewDisplaying();
			creo
			pfcView2D.View2D.Regenerate();
			pfcView2D.View2D.SetScale();*/
			//creo.session.RunMacro("~ Command `ProCmdRegenPart` ");

			//creo.connection.EventProcess();
			//solid.Save();
			//solid.Copy(name,null);
		} catch (Exception x)
		{  
			JOptionPane.showMessageDialog (new java.awt.Frame (),
                  "error at creo set parameters "+x +"\n"+x.getMessage(),
                  "foo",
                  JOptionPane.ERROR_MESSAGE);
		    System.out.println("pfcException: " + x);
		    x.printStackTrace ();
		}			
	}
	
	void sendSavedMsg()
	{
		busy = false;
		ValueCollection eventInfo = new ValueCollection();
		System.out.println("Sending ssaved msg for "+username);
		eventInfo.put(CommonPropertyNames.PROP_MESSAGE, new StringPrimitive(username));
		// Queue the event
		
		super.queueEvent("ModelSaved", DateTime.now(), eventInfo);
		try {
			super.updateSubscribedProperties(15000);
			super.updateSubscribedEvents(60000);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void save()
	{		
		try {
			String escapedUsername=username.replace('@', '_');
			creo.session.GetModelWindow(creo.session.GetCurrentModel()).Repaint();
			//(creo.session.GetModelWindow(creo.session.GetCurrentModel())).FlushCurrentWindow();
			//solid.Copy("temp",null);
			System.out.println("Exporting "+ username+".stl");
			creo.solid.Export(username+".stl", pfcModel.STLBinaryExportInstructions_Create(null));

			//pfcWindow.Window.refresh();
			JPEGImageExportInstructions instructions = pfcWindow.JPEGImageExportInstructions_Create (screenshotHeight, screenshotWidth); //h,w
			instructions.SetImageDepth (RasterDepth.RASTERDEPTH_24);
			instructions.SetDotsPerInch (DotsPerInch.RASTERDPI_300);//RASTERDPI_100);
			creo.session.ExportCurrentRasterImage (escapedUsername+".jpg", instructions);
			//creo.session.RunMacro("%" + "expjpga1jl" +";");
			
			try {
				parameters.put("sourceFile", new StringPrimitive(escapedUsername+".jpg"));
				parameters.put("targeFile", new StringPrimitive(escapedUsername+".jpg"));
				client.invokeService(ThingworxEntityTypes.Subsystems, "FileTransferSubsystem", "Copy", parameters, -1);
				//this.MoveFile(sourcePath, targetPath, overwrite);
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			creo.solid.Save();
			//solid.Export(username+".png", pfcModel.PNGBinaryExportInstructions_Create(null));
			//solid.Rename(username, true);
		} catch (jxthrowable e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}
}
