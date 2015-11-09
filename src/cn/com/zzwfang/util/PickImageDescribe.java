package cn.com.zzwfang.util;

import java.io.File;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * 获取图片描述
 * @author Noah
 *
 */
public class PickImageDescribe implements Parcelable {
	int		type;
	boolean	isCrop;
	File		file;
	int		outputX;
	int		outputY;
	int		aspectX;
	int		aspectY;
	
	/**
	 * 图片输出格式
	 */
	String	outputFormat;
	
	public PickImageDescribe(){
		
	}
	
	public PickImageDescribe(int type, boolean isCrop, File file, int outputX, int outputY, int aspectX, int aspectY, String outputFormat) {
		super();
		this.type = type;
		this.isCrop = isCrop;
		this.file = file;
		this.outputX = outputX;
		this.outputY = outputY;
		this.aspectX = aspectX;
		this.aspectY = aspectY;
		this.outputFormat = outputFormat;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt( type );
		dest.writeInt(isCrop() ? 1 : 0);
		dest.writeSerializable( file );
		dest.writeInt( outputX );
		dest.writeInt( outputY );
		dest.writeInt( aspectX );
		dest.writeInt( aspectY );
		dest.writeString( outputFormat );
		
	}
	public static final Parcelable.Creator<PickImageDescribe>	CREATOR	= new Creator<PickImageDescribe>() {
		public PickImageDescribe createFromParcel(Parcel source) {
			PickImageDescribe pickDescribe = new PickImageDescribe();
			pickDescribe.setType( source.readInt());
			pickDescribe.setCrop( source.readInt() > 0 ? true : false);
			pickDescribe.setFile( (File) source.readSerializable());
			pickDescribe.setOutputX( source.readInt());
			pickDescribe.setOutputY( source.readInt());
			pickDescribe.setAspectX( source.readInt());
			pickDescribe.setAspectY( source.readInt());
			pickDescribe.setOutputFormat( source.readString());
			return pickDescribe;
		}

		public PickImageDescribe[] newArray(int size) {
			return new PickImageDescribe[size];
		}
	};

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCrop() {
		return isCrop;
	}

	public void setCrop(boolean isCrop) {
		this.isCrop = isCrop;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getOutputX() {
		return outputX;
	}

	public void setOutputX(int outputX) {
		this.outputX = outputX;
	}

	public int getOutputY() {
		return outputY;
	}

	public void setOutputY(int outputY) {
		this.outputY = outputY;
	}

	public int getAspectX() {
		return aspectX;
	}

	public void setAspectX(int aspectX) {
		this.aspectX = aspectX;
	}

	public int getAspectY() {
		return aspectY;
	}

	public void setAspectY(int aspectY) {
		this.aspectY = aspectY;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public static Parcelable.Creator<PickImageDescribe> getCreator() {
		return CREATOR;
	}

	@Override
	public String toString() {
		return "PickDescribe [type=" + type + ", isCrop=" + isCrop + ", file=" + file + ", outputX=" + outputX + ", outputY=" + outputY + ", aspectX=" + aspectX + ", aspectY=" + aspectY + ", outputFormat=" + outputFormat + "]";
	}
	

}
