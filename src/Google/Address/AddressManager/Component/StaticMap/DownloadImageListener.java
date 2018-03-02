package Google.Address.AddressManager.Component.StaticMap;

import java.util.EventListener;

public interface DownloadImageListener extends EventListener {
	public void downloadImage(DownloadImageEvent e);
}
