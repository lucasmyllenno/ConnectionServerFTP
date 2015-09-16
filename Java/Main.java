import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Main {
	
	private String server = "hostname.com";
	private int port = 0;
    private String user = "user";
    private String password = "password";
    private FTPClient ftpClient;
	
	public void sync() throws Exception {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
        ftpClient.login(user, password);
        ftpClient.setFileTransferMode(FTP.BLOCK_TRANSFER_MODE);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        System.out.println("Conectado com sucesso!");
	}
	
	public void listFiles(String directory) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
		FTPFile[] ftpFile = ftpClient.listFiles();
		System.out.println("Quantidade de arquivos: " + (ftpFile.length-2) + " Lista: ");
		for (int i=2; i < ftpFile.length; i++)
			System.out.println(" "+ftpFile[i].getName());
	}
	
	public void uploadFile(String directory, String fileName, InputStream inputStream) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
		ftpClient.storeFile(fileName, inputStream);
        System.out.println("Arquivo enviado!");
	}
	
	public void newFile(String directory, String fileName, String text) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
        InputStream inputStream = new ByteArrayInputStream(text.getBytes());
        ftpClient.storeFile(fileName, inputStream);
        System.out.println("Arquivo criado!");
	}
	
	public void renameFile(String directory, String fileName, String fileNewName) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
		ftpClient.rename(fileName, fileNewName);
		System.out.println("Arquivo renomeado!");
	}
	
	public void removeFile(String directory, String fileName) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
		ftpClient.deleteFile(fileName);
		System.out.println("Arquivo exclu�do!");
	}
	
	public void closeSync() throws Exception {
		ftpClient.logout();
		System.out.println("Conex�o fechada!");
	}
	

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.sync();
			main.closeSync();
		} catch (Exception ex) {
			System.out.println("Erro: "+ex);
		}
	}
}
