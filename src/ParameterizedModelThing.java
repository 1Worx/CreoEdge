import javax.swing.JOptionPane;

//import com.ptc.pfc.pfcGlobal.pfcGlobal;
import com.ptc.pfc.pfcModelItem.Parameter;
import com.ptc.pfc.pfcModelItem.pfcModelItem;
import com.ptc.pfc.pfcSession.Session;
import com.ptc.pfc.pfcSolid.Solid;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinitions;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.constants.CommonPropertyNames;


@SuppressWarnings("serial")
public class ParameterizedModelThing extends VirtualThing implements Runnable {
	  Session session;
	  Solid solid;

	private Thread _shutdownThread = null;
	
	public ParameterizedModelThing(Session session, Solid solid, String name, String description, String identifier, ConnectedThingClient client) {
		super(name,description,identifier,client);

		this.solid = solid;
		this.session = session;
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
	@ThingworxServiceResult( name=CommonPropertyNames.PROP_RESULT, description="Result", baseType="STRING")
	//@ThingworxServiceResult( name=CommonPropertyNames.PROP_RESULT, description="Result", baseType="NUMBER" )
	public String generateModel( 
			@ThingworxServiceParameter( name="name", description="name", baseType="STRING" ) String name,
			@ThingworxServiceParameter( name="a", description="Value 1", baseType="INTEGER" ) Integer a,
			@ThingworxServiceParameter( name="b", description="Value 2", baseType="INTEGER" ) Integer b,
			@ThingworxServiceParameter( name="c", description="Value 3", baseType="INTEGER" ) Integer c,
			@ThingworxServiceParameter( name="d", description="Value 4", baseType="INTEGER" ) Integer d) throws Exception {

		setParameters(name,a.intValue(),b.intValue(),c.intValue(),d.intValue());
		return name;
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
		} catch (Exception x) {
			// Not much can be done if there is an exception here
			// In the case of production code should at least log the error
		}
	}

	void setParameters(String name, int a, int b , int c , int d)
	{
	      JOptionPane.showMessageDialog (new java.awt.Frame (),
	                "calling set parameters ",
	                "x",
	                JOptionPane.INFORMATION_MESSAGE);
		try {	
		      //session = pfcGlobal.GetProESession();	   
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
		                "got session, getting model " ,
		                "x",
		                JOptionPane.INFORMATION_MESSAGE);   
		      solid = (Solid) session.GetCurrentModel();
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
		                "got model " ,
		                "x",
		                JOptionPane.INFORMATION_MESSAGE);  
		      
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
		                " model " +solid,
		                "x",
		                JOptionPane.INFORMATION_MESSAGE);       
			Parameter p;
			p = solid.GetParam("A");
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
		                "settign parameter a " + a + " solid:"+solid,
		                "x",
		                JOptionPane.INFORMATION_MESSAGE);
		      
			p.SetValue (pfcModelItem.CreateIntParamValue(a));	
			
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
		                "settign parameter b",
		                "x",
		                JOptionPane.INFORMATION_MESSAGE);		      
			p = solid.GetParam("B");
			p.SetValue (pfcModelItem.CreateIntParamValue(b));
			p = solid.GetParam("C");
			p.SetValue (pfcModelItem.CreateIntParamValue(c));
			p = solid.GetParam("D");
			p.SetValue (pfcModelItem.CreateIntParamValue(d));

		      JOptionPane.showMessageDialog (new java.awt.Frame (),
	                "settign name parameter",
	                "x",
	                JOptionPane.INFORMATION_MESSAGE);
			p = solid.GetParam("NAME");
			p.SetValue (pfcModelItem.CreateStringParamValue(name));
			
			//RegenInstructions r = pfcSolid.RegenInstructions_Create(null,null,null);
			
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
	                  "regenrating",
	                  "x",
	                  JOptionPane.INFORMATION_MESSAGE);
			//r.SetRefreshModelTree(true);
			//r.SetForceRegen(true);
			//solid.Regenerate(null);
		    session.RunMacro("~ Command `ProCmdRegenPart` ");
			
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
	                  "saving",
	                  "x",
	                  JOptionPane.INFORMATION_MESSAGE);
			solid.Save();
			solid.Copy(name,null);
		} catch (Exception x)
		{  
			JOptionPane.showMessageDialog (new java.awt.Frame (),
                  "error at set "+x +"\n"+x.getMessage(),
                  "foo",
                  JOptionPane.ERROR_MESSAGE);
		    System.out.println("pfcException: " + x);
		    x.printStackTrace ();
		}			
	}

}
