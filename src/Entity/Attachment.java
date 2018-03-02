package Entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Attachment {

	private long id;
	private String type;

	private byte[] byteArray;
	private BufferedImage image;

	public Attachment(long id, String type, byte[] byteArray) {
		super();
		this.id = id;
		this.type = type;
		this.byteArray = byteArray;
	}

	public Attachment(String type, byte[] byteArray) {
		super();
		this.type = type;
		this.byteArray = byteArray;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public BufferedImage getImage() {
		InputStream in = new ByteArrayInputStream(this.getByteArray());

		try {
			this.image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
