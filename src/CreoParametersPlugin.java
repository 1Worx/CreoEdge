import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.swing.JOptionPane;
import com.ptc.cipjava.jxthrowable;
import com.ptc.pfc.pfcAsyncConnection.AsyncActionListener_u;
import com.ptc.pfc.pfcAsyncConnection.AsyncConnection;
import com.ptc.pfc.pfcAsyncConnection.TerminationStatus;
import com.ptc.pfc.pfcAsyncConnection.pfcAsyncConnection;
import com.ptc.pfc.pfcBase.ActionListener;
import com.ptc.pfc.pfcFeature.Feature;
import com.ptc.pfc.pfcModel.DefaultModelActionListener;
import com.ptc.pfc.pfcModel.DefaultModelEventActionListener;
import com.ptc.pfc.pfcModel.ModelDescriptor;
import com.ptc.pfc.pfcSession.Session;
import com.ptc.pfc.pfcSolid.DefaultSolidActionListener;
import com.ptc.pfc.pfcSolid.Solid;
import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.ConnectionException;
import com.thingworx.communications.client.things.filetransfer.FileTransferVirtualThing;
import com.thingworx.communications.common.SecurityClaims;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.primitives.BooleanPrimitive;
import com.thingworx.types.primitives.StringPrimitive;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CreoParametersPlugin extends AsyncActionListener_u {
	  public static Session session=null;
	  public static Solid solid=null;
	  static ConnectedThingClient client;
	  static String uri;
	  public boolean exitflag = false;
	  AsyncParameterizedModelThing plugin;
	  String openMacro = 
"mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `DLG_PREVIEW_POST` `file_open`;\\"+
"mapkey(continued) ~ Trail `UI Desktop` `UI Desktop` `PREVIEW_POPUP_TIMER` \\"+
"mapkey(continued) `file_open:Ph_list.Filelist:<NULL>`;\\"+
"mapkey(continued) ~ Update `file_open` `Inputname` `PUZZLE`;\\"+
"mapkey(continued) ~ Command `ProFileSelPushOpen_Standard@context_dlg_open_cmd`;";
	  public  AsyncConnection connection = null;
	  ActionListener a,b,c;
	  
	  private static Logger log = Logger.getLogger(CreoParametersPlugin.class.getName());
	  
		public static void main(String[] args) throws Exception {
			/*Properties props = new Properties();
	         try {
           	 props.load(new FileInputStream("creoconfig.txt"));
            }
            catch(IOException e)
            {
           	 e.printStackTrace();
            }
			
			ClientConfigurator config = new ClientConfigurator();
			uri = "ws://"+props.getProperty("ip")+":"+props.getProperty("port")+"/Thingworx/WS";
			config.setUri(uri);
			// Reconnect every 15 seconds if a disconnect occurs or if initial connection cannot be made
			config.setReconnectInterval(15);
			
			// Set the security using an Application Key
			String appKey = props.getProperty("appKey");// "482d05fb-d0da-4b36-a21d-43f2bebe7e78";
			SecurityClaims claims = SecurityClaims.fromAppKey(appKey);
			config.setSecurityClaims(claims);
			
			// Set the name of the client
			String boundName = props.getProperty("clientName");// "CreoPluginGateway";
			config.setName(boundName);
			// This client is a SDK
			//config.setAsSDKType();
			
			try {
			String ID = "creo1";
			client = new ConnectedThingClient(config);
			int w = Integer.parseInt(props.getProperty("screenshotWidth"));
			int h = Integer.parseInt(props.getProperty("screenshotHeight"));
			HashMap<String, String> virtualDirs = new HashMap<String, String>();
			String userDir = "C:/christiaan/projects";//System.getProperty("user.dir");
			               
			virtualDirs.put("logs", userDir );
			virtualDirs.put("incomming", userDir);
			//ParameterizedModelThing p = new ParameterizedModelThing(session,solid,"model","description",ID,client);
			FileTransferVirtualThing p = new FileTransferVirtualThing("CreoEdgeServer","description",client,virtualDirs);
			client.bindThing(p);

		    client.start();
		    Thread.sleep(5000);
		      
			ValueCollection parameters = new ValueCollection(); 
			//parameters.put("sourceFile", new StringPrimitive("demo_09.png"));
			parameters.put("sourcePath", new StringPrimitive("/logs"));
			parameters.put("sourceRepo", new StringPrimitive(client.getGatewayName()));
			//parameters.put("targeFile", new StringPrimitive("pic1.png"));
			parameters.put("targetPath", new StringPrimitive("\\Thingworx\\MediaEntities"));
			parameters.put("targetRepo", new StringPrimitive("SystemRepository"));
			
			try {
				String fileName = "putty1.png";
				parameters.put("sourceFile", new StringPrimitive(fileName));
				parameters.put("targeFile", new StringPrimitive(fileName));
				InfoTable infoTable = client.invokeService(ThingworxEntityTypes.Subsystems, "FileTransferSubsystem", "Copy", parameters, -1);
		
			  BooleanPrimitive isTransferComplete =
			          (BooleanPrimitive) infoTable.getFirstRow().getPrimitive("isComplete");
			      if (!isTransferComplete.getValue()) {
			        String errorMessage = infoTable.getFirstRow().getStringValue("message");
			        log.log(Level.SEVERE, errorMessage);
			      } else {
			        log.info(fileName + "-File transfer Ends at " + Calendar.getInstance().getTime());
			        // deleteFile(prop.getProperty("LOCAL_DIR") + File.separator
			        // + fileName);
			      }
			      //client.shutdown();
			    } catch (Exception e) {
			      log.log(Level.SEVERE, e.getMessage(), e);
			    }
			    */
		   /* 
			 
			FileTransferVirtualThing fthing = new FileTransferVirtualThing("CreoEdgeServer", "Makerbot File Transfer", client, virtualDirs);
			//fthing.MoveFile(sourcePath, targetPath, overwrite);

			client.bindThing(fthing);
			
			} catch (Exception x)
			{
           	 x.printStackTrace();
			}
			*/
			//uri = args[0];
			try {
				System.loadLibrary("pfcasyncmt");
			} catch (UnsatisfiedLinkError e)
			{
				printMsg("could not load pfcasyncmt.dll");
				return;
			}
			try {
			new CreoParametersPlugin();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	  String closeMacro = "";
	  public static void start ()
	  {
	    try {
	     // session = pfcGlobal.GetProESession();	      
	     /// solid = (Solid) session.GetCurrentModel();
	      //setParameters("fo",1,2,3,4);
	     // initEdgeServer();
	    }
	    catch (Exception x)
	      {
		      JOptionPane.showMessageDialog (new java.awt.Frame (),
	                  "error "+x,
	                  "Release Checking Error",
	                  JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception caught: "+x);
			x.printStackTrace();
			//System.exit(-1);
	      }
	  }			

	  public static void stop ()
	  {
	    try{	     
	    	//solid.Erase();	    
	    	if ( client != null)
	    		client.shutdown();
	    }
	    catch(Exception x){
	      System.out.println("pfcException: " + x);
	      x.printStackTrace ();
	    }
	  }


		void initEdgeServer() throws Exception
		{
	         Properties props = new Properties();
	         try {
            	 props.load(new FileInputStream("creoconfig.txt"));
             }
             catch(IOException e)
             { 
            	 e.printStackTrace();
             }
           			
			ClientConfigurator config = new ClientConfigurator();
			uri = "ws://"+props.getProperty("ip")+":"+props.getProperty("port")+"/Thingworx/WS";
			config.setUri(uri);
			// Reconnect every 15 seconds if a disconnect occurs or if initial connection cannot be made
			config.setReconnectInterval(15);
			
			// Set the security using an Application Key
			String appKey = props.getProperty("appKey");// "482d05fb-d0da-4b36-a21d-43f2bebe7e78";
			SecurityClaims claims = SecurityClaims.fromAppKey(appKey);
			config.setSecurityClaims(claims);
			
			// Set the name of the client
			String boundName = props.getProperty("clientName");// "CreoPluginGateway";
			config.setName(boundName);
			// This client is a SDK
			//config.setAsSDKType();
			
			try {
			String ID = "creo1";
			client = new ConnectedThingClient(config);
			//solid = (Solid) session.GetCurrentModel();
			//int w = Integer.parseInt(props.getProperty("screenshotWidth"));
			//int h = Integer.parseInt(props.getProperty("screenshotHeight"));
			HashMap<String, String> virtualDirs = new HashMap<String, String>();
			String userDir = Paths.get("").toAbsolutePath().toString();//"C:\\christiaan\\projects";//System.getProperty("user.dir");
			               
			virtualDirs.put("logs", userDir );
			virtualDirs.put("incomming", userDir);
			//ParameterizedModelThing p = new ParameterizedModelThing(session,solid,"model","description",ID,client);
			plugin = new AsyncParameterizedModelThing(this,"model","description",ID,client,virtualDirs);
			client.bindThing(plugin);
		   /* 
			 
			FileTransferVirtualThing fthing = new FileTransferVirtualThing("CreoEdgeServer", "Makerbot File Transfer", client, virtualDirs);
			//fthing.MoveFile(sourcePath, targetPath, overwrite);

			client.bindThing(fthing);*/
			
			client.start();
			} catch (Exception x)
			{
				JOptionPane.showMessageDialog (new java.awt.Frame (),
	                  "error at creo edge server "+x +"\n"+x.getMessage(),
	                  "foo",
	                  JOptionPane.ERROR_MESSAGE);
			    System.out.println("pfcException: " + x);
			    x.printStackTrace ();
			}
		   /*   JOptionPane.showMessageDialog (new java.awt.Frame (),
	                  "initialize completed!",
	                  "x",
	                  JOptionPane.INFORMATION_MESSAGE);*/
			
		}
		
		public void disconnect()
		{
			try {	
				System.out.println("Disconnecting");
				connection.EventProcess();
				session.RemoveActionListener(a);
				session.RemoveActionListener(b);
				session.RemoveActionListener(c);
				System.gc();
				Thread.sleep(500);
				connection.Disconnect(5);//timeout in seconds
				connection = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				printMsg("error message: " +e.getMessage());
			}
		}
		
		public void connect()
		{
			if ( connection != null )
				disconnect();	
			System.out.println("Connecting");
			
			try 
			{
				connection = pfcAsyncConnection.AsyncConnection_Connect(null, null, null, null);

				System.out.println("Getting session");
				session = connection.GetSession();
				solid = (Solid) session.GetCurrentModel();
			} catch (Exception e)
			{
				e.printStackTrace();
				printMsg("Cant get session");
				return;
			}
			try {
			//connection.AddActionListener(this);
			//only save after regen

				System.out.println("add action listenetrs");
			a = 
			new DefaultSolidActionListener(){
				@Override
				public void OnAfterRegen(Solid arg0, Feature arg1, boolean arg2) throws jxthrowable {
					plugin.save();					
				}
			};
			session.AddActionListener(a);

			b = new DefaultModelEventActionListener() {
			@Override
			public void OnAfterModelCopyAll(ModelDescriptor arg0,ModelDescriptor arg1) throws jxthrowable {
				// TODO Auto-generated method stub
				
					//if ( arg0.GetFileName() == "puzzle.prt" ) // only done when the copy is saved
					//return;
					plugin.sendSavedMsg();	
					JOptionPane.showMessageDialog (new java.awt.Frame (),
			                  "sending copy event "+ arg0.GetFileName(),
			                  "foo",
			                  JOptionPane.ERROR_MESSAGE);
			}
			};

			session.AddActionListener(b);			
			
			c = new DefaultModelActionListener() {
				 
				@Override
				public void OnAfterModelSaveAll(ModelDescriptor arg0) throws jxthrowable {
					// TODO Auto-generated method stub
				
						//if ( arg0.GetFileName() == "puzzle.prt" ) // only done when the copy is saved
						//return;
						plugin.sendSavedMsg();
				}
			};
			session.AddActionListener(c);

			System.out.println("Event process");
			
			connection.EventProcess();
			//linitEdgeServer();			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		CreoParametersPlugin()
		{
			//connect();
			try {
			initEdgeServer();
			while(!exitflag)
			{
				if ( (connection!=null) && (plugin.busy==false))
					connection.EventProcess();
				Thread.sleep(500);
				if (System.in.read()=='q')
					exitflag = true;					
			}
			System.out.println("exiting");
			//wait so proe can finish shiutting down
			Thread.sleep(2000);
			if ( connection!=null)
				connection.EventProcess();
			
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		@Override
		public void OnTerminate(TerminationStatus arg0) throws jxthrowable {
			System.out.println("exitflag = true;");
			exitflag = true;			
		}
		static void printMsg(String s)
		{
			System.out.println(s);
		}

}
