import java.util.HashMap;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.filetransfer.FileTransferVirtualThing;

public class FileTransfer extends FileTransferVirtualThing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileTransfer(String name, String description,
            ConnectedThingClient client, HashMap<String, String> virtualDirectories)
	{
		super(name, description, client, virtualDirectories);
	}
}
