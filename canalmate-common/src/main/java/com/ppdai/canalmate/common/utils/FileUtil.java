package com.ppdai.canalmate.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class FileUtil {
	public static void copyFileChannel(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();
			out = fo.getChannel();
			in.transferTo(0L, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				String txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				}
				else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txts;
	}

	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			File myFilePath = new File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	public static void createNewFile(String file) {
		try {
			File f = new File(file);
			if (f.exists()) {
				f.delete();
			}

			f = new File(file);
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("创建文件失败！");
		}
	}

	public static void writeFile(String file, String content, boolean append) {
		FileWriter fw = null;
		try {
			File f = new File(file);
			if (!f.exists()) {
				System.out.println(" created filefolder： " + file.substring(0, file.lastIndexOf("/")));
				FileUtil.createFolder(file.substring(0, file.lastIndexOf("/")));
			}

			fw = new FileWriter(f, append);
			String c = content + "\r\n";
			fw.write(c);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("写入失败");
			System.exit(-1);

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void delFile(String fileName) {
		try {
			String filePath = fileName;
			File delFile = new File(filePath);
			delFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			File myFilePath = new File(filePath);

			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] childFiles = file.list();
		File temp = null;
		for (int i = 0; i < childFiles.length; i++) {
			if (path.endsWith(File.separator))
				temp = new File(path + childFiles[i]);
			else {
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + childFiles[i]);
				delFolder(path + "/" + childFiles[i]);
			}
		}
	}

	public static Set<String> readFile(File file) throws Exception {
		Set rtnSet = new HashSet();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				rtnSet.add(line.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		}

		return rtnSet;
	}

	public static Map<String, Set<String>> readFile(String path, int dealNum) throws Exception {
		Map datas = new HashMap();
		Set dealSet = new HashSet();
		Set remainSet = new HashSet();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String line = null;
			int i = 1;
			while ((line = br.readLine()) != null) {
				if (i <= dealNum)
					dealSet.add(line);
				else {
					remainSet.add(line);
				}
				i++;
			}
			if (dealSet.size() > 0) {
				datas.put("dealListKey", dealSet);
			}
			if (remainSet.size() > 0) {
				datas.put("remainListKey", remainSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		}

		return datas;
	}

	public static void getAllFiles(File dir, List<File> rtnFiles) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getAllFiles(files[i], rtnFiles);
			} else {
				rtnFiles.add(files[i]);
			}
		}
	}

	public static void main(String[] args) {
		// writeFile(" D:\\myStockLog\\HistoryDateYahoo\\2015-07-01\\RealTimeData.log",
		// "ccccccccccccc", false);
		FileUtil.writeFile("C:\\gaa.txt", "aaa", true);
	}
}
