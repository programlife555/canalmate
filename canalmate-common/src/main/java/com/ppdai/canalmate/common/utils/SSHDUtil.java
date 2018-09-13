package com.ppdai.canalmate.common.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.session.ClientSession;

import net.schmizz.sshj.SSHClient;

import net.schmizz.sshj.Config;
import net.schmizz.sshj.DefaultConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

//https://www.programcreek.com/java-api-examples/?api=net.schmizz.sshj.SSHClient
public class SSHDUtil {
	 public static void main(String[] args) throws Exception {
		 SSHClient ssh = new SSHClient();
	        ssh.loadKnownHosts();
	        ssh.connect("IP",22);
	        try {
	            //ssh.authPublickey(System.getProperty("user.name"));
	        	ssh.authPublickey("hadoop");
	        	//ssh.addHostKeyVerifier(new PromiscuousVerifier());
	        	ssh.loadKnownHosts(new File("C:\\Users\\testuser\\.ssh\\known_hosts"));
	        	///home/x/.ssh/known_hosts
	            // Present here to demo algorithm renegotiation - could have just put this before connect()
	            // Make sure JZlib is in classpath for this to work
	            ssh.useCompression();

	            final String src = System.getProperty("user.home") + File.separator + "test_file";
	            ssh.newSCPFileTransfer().upload(new FileSystemFile(src), "/tmp/");
	        } finally {
	            ssh.disconnect();
	        }
	 }
//	 public static void clentTest() throws IOException
//	    {
//	        String cmd="ifconfig";
//	        SshClient client=SshClient.setUpDefaultClient();
//	        client.start();
//	        ClientSession session=client.connect("bellring", "IP", 22).await().getSession();
//	        session.addPasswordIdentity("bellring");
//	        //session.addPublicKeyIdentity(SecurityUtils.loadKeyPairIdentity("keyname", new FileInputStream("priKey.pem"), null));
//	        if(!session.auth().await().isSuccess())
//	            System.out.println("auth failed");
//
//	        ChannelExec ec=session.createExecChannel(cmd);
//	        ec.setOut(System.out);
//	        ec.open();
//	        ec.waitFor(ClientChannel.CLOSED, 0);
//	        ec.close();
//
//	        client.stop();
//	    }
//
}