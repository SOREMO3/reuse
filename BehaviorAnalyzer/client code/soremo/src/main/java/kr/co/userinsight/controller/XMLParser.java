package kr.co.userinsight.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.ac.yonsei.JARVIS;
import kr.co.userinsight.Utils;
import kr.co.userinsight.model.RDStructureModel;

public class XMLParser {
	private final String TITLE_DELIM = "of";
	
	public XMLParser() {

	}

	/**
	 * TODO: 1. nested tag �뜝�떩�눦�삕 �뜝�떗�슱�삕 2. XPath�뜝�룞�삕 �솗�뜝�룞�삕?
	 * 
	 * @param file
	 * @return
	 */
	public RDStructureModel parse(File file) {

		RDStructureModel model = this.parseTextRD(file);

		if (model != null) {
			return model;
		}
		return null;
	}

	/**
	 * Processing text RD TODO: �뜝�룞�삕泥� RDML �뜝�떩�눦�삕 �뜝�룞�삕�뜝占� �뜝�룞�삕�뜝�룞�삕 �뜝�떗�슱�삕
	 * 
	 * @param file
	 * @return
	 */

	// NAM - START //
	
	public boolean isRDNull(String uri2) {//uri는 http://13.124.180.8/rdml/{rd_id}
		
		String rdID = null;
		String uri = "http://13.124.180.8/rdml/" + uri2;
		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(uri);
			doc2.getDocumentElement().normalize();

			NodeList tmpList = doc2.getElementsByTagName("RD");

				Node tmpNode = tmpList.item(0);
				Element tmpElement = (Element) tmpNode;

				rdID = tmpElement.getAttribute("id");

		}catch (Exception e) {	
			return true;
		}
		return false;
	}

	public String checkParent(String uri2) {//uri는 http://13.124.180.8/rdml/parent/{rd_id}
		
		String rdID = null;
		String uri = "http://13.124.180.8/rdml/parent/"+uri2;
		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(uri);
			doc2.getDocumentElement().normalize();

			NodeList tmpList = doc2.getElementsByTagName("RD");

				Node tmpNode = tmpList.item(0);
				Element tmpElement = (Element) tmpNode;

				rdID = tmpElement.getAttribute("id");

		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rdID;
	}
	
	public List<String> parseRDVersionList(String tarRD){
		ArrayList<String> rdVersionListTmp1 = new ArrayList<String>();
		ArrayList<String> rdVersionListTmp2 = new ArrayList<String>();
		ArrayList<String> rdVersionListTmp3 = new ArrayList<String>();
		List<String> rdVersionListTmp4 = new ArrayList<String>();
		List<String> rdVersionList = new ArrayList<String>();
		
		StringBuilder queryResult = new StringBuilder();
		String temp = null;
		StringTokenizer st = null; 
		try{
			URL url = new URL("http://13.124.180.8/rdml/rd/versions/"+tarRD);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			String line;

			while ((line = rd.readLine()) != null) {
				queryResult.append(line);
			} 
			
			temp = queryResult.toString();
			st = new StringTokenizer(temp, ",");

						
			while(st.hasMoreTokens()){
				rdVersionListTmp1.add(st.nextToken());
			}
			
			temp = rdVersionListTmp1.toString();
			String[] arr = temp.split("Version = ");
			for(int a=0; a<arr.length; a++){
				if(a>0){
					String[] arr2 = arr[a].split(", ");
					rdVersionListTmp2.add(arr2[0]);
				}
			}
			
			temp = rdVersionListTmp2.toString();
			String[] arr1 = temp.split("\"");
			for(int b=0; b<arr1.length; b++){
					rdVersionListTmp3.add(arr1[b]);
			}
			
			for(int j=0; j<rdVersionListTmp3.size(); j++){
				String tempStr=null;
				tempStr = rdVersionListTmp3.get(j).replace("[", "");
				tempStr = tempStr.replace("]", "");
				tempStr = tempStr.replace(" ", "");
				tempStr = tempStr.replace(",", "");
				rdVersionListTmp4.add(tempStr);
			}
			rdVersionListTmp4 = rdVersionListTmp4.stream().distinct().collect(Collectors.toList());
			
			for(int j=0; j<rdVersionListTmp4.size(); j++){
				if(rdVersionListTmp4.get(j).contains("."))
					rdVersionList.add(rdVersionListTmp4.get(j));
			}
			
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
		
		return rdVersionList;
	}
	public RDStructureModel parseOneTextRD(String uri){
		RDStructureModel resultModel = new RDStructureModel();

		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(uri);
			doc2.getDocumentElement().normalize();

		
			RDStructureModel rd = null;
			
				Node tmpNode = doc2.getElementsByTagName("RD").item(0);
				Element tmpElement = (Element) tmpNode;

				rd = new RDStructureModel();
				rd.setId(tmpElement.getAttribute("id"));
				rd.setTitle(tmpElement.getAttribute("title"));
				rd.setVersion(tmpElement.getAttribute("version") + "");
				rd.setCreator(tmpElement.getAttribute("creator") + "");
				rd.setCreatedDate(tmpElement.getAttribute("createdDate") + "");
				rd.setModifier(tmpElement.getAttribute("modifier") + "");
				rd.setModifiedDate(tmpElement.getAttribute("modifiedDate") + "");
				rd.setApplication(tmpElement.getAttribute("application") + "");
				rd.setDepth(Integer.parseInt(tmpElement.getAttribute("depth")));


				Node tmpNode2 = doc2.getElementsByTagName("Annotation").item(0);


				if (tmpNode2.getNodeType() == Node.ELEMENT_NODE) {
					Element tmpElement2 = (Element) tmpNode2;

					Node relationType = tmpElement2.getElementsByTagName("RelationTypes").item(0);
					Element RelationTypesElmnt = (Element) relationType;

					Node Type = RelationTypesElmnt.getFirstChild();
					if (Type != null) {

						rd.getAnnotation().setRelationTypes(Type.getNodeValue());
					}

					NodeList aiItem = tmpElement.getElementsByTagName("AIContents");
					Element AIItemElmnt = (Element) aiItem.item(0);
					Node Item = AIItemElmnt.getFirstChild();
					if (Item != null) {

						rd.getAnnotation().setAIContents(Item.getNodeValue());
					}

					NodeList process = tmpElement.getElementsByTagName("Process");
					Element ProcessElmnt = (Element) process.item(0);
					Node proc = ProcessElmnt.getFirstChild();
					if (proc != null) {

						rd.getAnnotation().setTmpProcess(proc.getNodeValue());
					}
					
					NodeList numOfContents = tmpElement.getElementsByTagName("NumOfContents");
					Element numOfContentsElmnt = (Element) numOfContents.item(0);
					NodeList nOClist = numOfContentsElmnt.getElementsByTagName("NumOfWords");
					Element numOfContentsElmnt2 = (Element) nOClist.item(0);
					Node nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfWords(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfTables");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfTables(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfImages");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfImages(nOC.getNodeValue());
					}
					
			}

				Node tmpContent = doc2.getElementsByTagName("Contents").item(0);
					
					if(tmpContent.getNodeType() == Node.ELEMENT_NODE){
						Node Type = tmpContent.getFirstChild();
						if (Type != null) {
							rd.setContents(Type.getNodeValue());
						}	
					}
			resultModel = rd;	
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}	
		return resultModel;
	}
	
	public ArrayList<RDStructureModel> parseTextRDList(String uri) {
		
		ArrayList<RDStructureModel> rdList = null;
		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(uri);
			doc2.getDocumentElement().normalize();

			rdList = new ArrayList<RDStructureModel>();
			RDStructureModel rd = null;
			NodeList tmpList = doc2.getElementsByTagName("RD");

			for (int i = 0; i < tmpList.getLength(); i++) {
				Node tmpNode = tmpList.item(i);
				Element tmpElement = (Element) tmpNode;

				rd = new RDStructureModel();
				rd.setId(tmpElement.getAttribute("id"));
				rd.setTitle(tmpElement.getAttribute("title"));
				rd.setVersion(tmpElement.getAttribute("version") + "");
				rd.setCreator(tmpElement.getAttribute("creator") + "");
				rd.setCreatedDate(tmpElement.getAttribute("createdDate") + "");
				rd.setModifier(tmpElement.getAttribute("modifier") + "");
				rd.setModifiedDate(tmpElement.getAttribute("modifiedDate") + "");
				rd.setApplication(tmpElement.getAttribute("application") + "");
				rd.setDepth(Integer.parseInt(tmpElement.getAttribute("depth")));

				rdList.add(rd);
			}

			NodeList tmpList2 = doc2.getElementsByTagName("Annotation");
			if(tmpList2.getLength()>0){
			for (int i = 0; i < tmpList2.getLength(); i++) {
				Node tmpNode2 = tmpList2.item(i);

				if (tmpNode2.getNodeType() == Node.ELEMENT_NODE) {
					Element tmpElement = (Element) tmpNode2;

					NodeList relationType = tmpElement.getElementsByTagName("RelationTypes");
					Element RelationTypesElmnt = (Element) relationType.item(0);

					Node Type = RelationTypesElmnt.getFirstChild();
					if (Type != null) {

						rdList.get(i).getAnnotation().setRelationTypes(Type.getNodeValue());
					}

					NodeList aiItem = tmpElement.getElementsByTagName("AIContents");
					Element AIItemElmnt = (Element) aiItem.item(0);
					Node Item = AIItemElmnt.getFirstChild();
					if (Item != null) {

						rdList.get(i).getAnnotation().setAIContents(Item.getNodeValue());
					}

					NodeList process = tmpElement.getElementsByTagName("Process");
					Element ProcessElmnt = (Element) process.item(0);
					Node proc = ProcessElmnt.getFirstChild();
					if (proc != null) {

						rdList.get(i).getAnnotation().setTmpProcess(proc.getNodeValue());
					}
					
					NodeList numOfContents = tmpElement.getElementsByTagName("NumOfContents");
					Element numOfContentsElmnt = (Element) numOfContents.item(0);
					NodeList nOClist = numOfContentsElmnt.getElementsByTagName("NumOfWords");
					Element numOfContentsElmnt2 = (Element) nOClist.item(0);
					Node nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfWords(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfTables");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfTables(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfImages");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfImages(nOC.getNodeValue());
					}
				}
				}
			}

			NodeList tmpListforContent = doc2.getElementsByTagName("Contents");
			
			//猷⑦듃 rd�뒗 contents �깭洹멸� �뾾�쓬
				for (int i = 1; i < rdList.size(); i++) {
					Node tmpNode=null;
					tmpNode = tmpListforContent.item(i-1);
					
					if (tmpNode.getNodeType() == Node.ELEMENT_NODE) {
						if(tmpNode.getFirstChild() == null){
							rdList.get(i).setContents("");  // Contents가 null 값이면 ""을 넣음
						}
						else{
							Node Type = tmpNode.getFirstChild();
							rdList.get(i).setContents(Type.getNodeValue());
						}
					}
				}
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rdList;
	}
	// NAM - END //
	
	
	public RDStructureModel parseTextRD(String uri) {
		RDStructureModel resultModel = new RDStructureModel();

		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(uri);
			doc2.getDocumentElement().normalize();

			ArrayList<RDStructureModel> rdList = new ArrayList<RDStructureModel>();
			RDStructureModel rd = null;
			NodeList tmpList = doc2.getElementsByTagName("RD");

			for (int i = 0; i < tmpList.getLength(); i++) {
				Node tmpNode = tmpList.item(i);
				Element tmpElement = (Element) tmpNode;

				rd = new RDStructureModel();
				rd.setId(tmpElement.getAttribute("id"));
				rd.setTitle(tmpElement.getAttribute("title"));
				rd.setVersion(tmpElement.getAttribute("version") + "");
				rd.setCreator(tmpElement.getAttribute("creator") + "");
				rd.setCreatedDate(tmpElement.getAttribute("createdDate") + "");
				rd.setModifier(tmpElement.getAttribute("modifier") + "");
				rd.setModifiedDate(tmpElement.getAttribute("modifiedDate") + "");
				rd.setApplication(tmpElement.getAttribute("application") + "");
				rd.setDepth(Integer.parseInt(tmpElement.getAttribute("depth")));

				rdList.add(rd);
			}

			NodeList tmpList2 = doc2.getElementsByTagName("Annotation");
			if(tmpList2.getLength()>0){
			for (int i = 0; i < tmpList2.getLength(); i++) {
				Node tmpNode2 = tmpList2.item(i);

				if (tmpNode2.getNodeType() == Node.ELEMENT_NODE) {
					Element tmpElement = (Element) tmpNode2;

					NodeList relationType = tmpElement.getElementsByTagName("RelationTypes");
					Element RelationTypesElmnt = (Element) relationType.item(0);

					Node Type = RelationTypesElmnt.getFirstChild();
					if (Type != null) {

						rdList.get(i).getAnnotation().setRelationTypes(Type.getNodeValue());
					}

					NodeList aiItem = tmpElement.getElementsByTagName("AIContents");
					Element AIItemElmnt = (Element) aiItem.item(0);
					Node Item = AIItemElmnt.getFirstChild();
					if (Item != null) {

						rdList.get(i).getAnnotation().setAIContents(Item.getNodeValue());
					}

					NodeList process = tmpElement.getElementsByTagName("Process");
					Element ProcessElmnt = (Element) process.item(0);
					Node proc = ProcessElmnt.getFirstChild();
					if (proc != null) {

						rdList.get(i).getAnnotation().setTmpProcess(proc.getNodeValue());
					}
					
					NodeList numOfContents = tmpElement.getElementsByTagName("NumOfContents");
					Element numOfContentsElmnt = (Element) numOfContents.item(0);
					NodeList nOClist = numOfContentsElmnt.getElementsByTagName("NumOfWords");
					Element numOfContentsElmnt2 = (Element) nOClist.item(0);
					Node nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfWords(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfTables");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfTables(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfImages");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfImages(nOC.getNodeValue());
					}
				}
			}

			}

			NodeList tmpListforContent = doc2.getElementsByTagName("Contents");
			
				for (int i = 0; i < tmpListforContent.getLength(); i++) {
					Node tmpNode = tmpListforContent.item(i);

					if (tmpNode.getNodeType() == Node.ELEMENT_NODE) {

						Node Type = tmpNode.getFirstChild();
						if (Type != null) {
							rdList.get(i).setContents(Type.getNodeValue());
							
						}
					}
				}
				
			resultModel = Utils.rdParentAlgorithm(rdList);
		} catch (

		ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultModel;
	}

	private RDStructureModel parseTextRD(File file) {
		RDStructureModel resultModel = new RDStructureModel();

		try {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
			Document doc2 = docBuild.parse(file);
			doc2.getDocumentElement().normalize();

			ArrayList<RDStructureModel> rdList = new ArrayList<RDStructureModel>();
			RDStructureModel rd = null;
			NodeList tmpList = doc2.getElementsByTagName("RD");

			for (int i = 0; i < tmpList.getLength(); i++) {
				Node tmpNode = tmpList.item(i);
				Element tmpElement = (Element) tmpNode;

				rd = new RDStructureModel();
				rd.setId(tmpElement.getAttribute("id"));
				rd.setTitle(tmpElement.getAttribute("title"));
				rd.setVersion(tmpElement.getAttribute("version") + "");
				rd.setCreator(tmpElement.getAttribute("creator") + "");
				rd.setCreatedDate(tmpElement.getAttribute("createdDate") + "");
				rd.setModifier(tmpElement.getAttribute("modifier") + "");
				rd.setModifiedDate(tmpElement.getAttribute("modifiedDate") + "");
				rd.setApplication(tmpElement.getAttribute("application") + "");
				rd.setDepth(Integer.parseInt(tmpElement.getAttribute("depth")));

				rdList.add(rd);
			}

			NodeList tmpList2 = doc2.getElementsByTagName("Annotation");

			for (int i = 0; i < tmpList2.getLength(); i++) {
				Node tmpNode2 = tmpList2.item(i);

				if (tmpNode2.getNodeType() == Node.ELEMENT_NODE) {
					Element tmpElement = (Element) tmpNode2;

					NodeList relationType = tmpElement.getElementsByTagName("RelationTypes");
					Element RelationTypesElmnt = (Element) relationType.item(0);

					Node Type = RelationTypesElmnt.getFirstChild();
					if (Type != null) {

						rdList.get(i).getAnnotation().setRelationTypes(Type.getNodeValue());

					}

					NodeList aiItem = tmpElement.getElementsByTagName("AIContents");
					Element AIItemElmnt = (Element) aiItem.item(0);
					Node Item = AIItemElmnt.getFirstChild();
					if (Item != null) {

						rdList.get(i).getAnnotation().setAIContents(Item.getNodeValue());
					}

					NodeList process = tmpElement.getElementsByTagName("Process");
					Element ProcessElmnt = (Element) process.item(0);
					Node proc = ProcessElmnt.getFirstChild();
					if (proc != null) {

						rdList.get(i).getAnnotation().setTmpProcess(proc.getNodeValue());
					}
					
					NodeList numOfContents = tmpElement.getElementsByTagName("NumOfContents");
					Element numOfContentsElmnt = (Element) numOfContents.item(0);
					NodeList nOClist = numOfContentsElmnt.getElementsByTagName("NumOfWords");
					Element numOfContentsElmnt2 = (Element) nOClist.item(0);
					Node nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfWords(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfTables");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfTables(nOC.getNodeValue());
					}
					
					nOClist = numOfContentsElmnt.getElementsByTagName("NumOfImages");
					numOfContentsElmnt2 = (Element) nOClist.item(0);
					nOC = numOfContentsElmnt2.getFirstChild();
					if (nOC != null) {

						rd.getAnnotation().setNumOfImages(nOC.getNodeValue());
					}
				}

			}

			int depthTemp = 1;

			RDStructureModel rdPointer = rdList.get(0);

			for (int i = depthTemp; i < rdList.size(); i++) {

				if (rdList.get(i).getDepth() == depthTemp) {
					rdList.get(i).setParent(rdPointer);
					rdPointer.getNestedRD().add(rdList.get(i));
				} else if (rdList.get(i - 1).getDepth() > rdList.get(i).getDepth()) {
					depthTemp--;
					i--;
					rdPointer = rdList.get(depthTemp - 2);
				} else {
					depthTemp++;
					i--;
					rdPointer = rdList.get(i);
				}
			}
			resultModel = rdList.get(0);

		} catch (

		ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultModel;
	}
	
	public static String toString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}

	/**
	 * Source RD �뜝�떩�눦�삕
	 * 
	 * @param file
	 * @return
	 */
	// private RDMLModel parseSourceRD(File file) {
	// RDMLModel resultModel = new RDMLModel(RDMLModel.RD_SOURCE);
	//
	// resultModel.setPath(file.toString());
	//
	// try {
	// String codeInfo = new
	// String(Files.readAllBytes(Paths.get(file.getCanonicalPath())), "UTF-8");
	// String packageInfo = "";
	//
	// try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	// for (String eachLine; (eachLine = br.readLine()) != null;) {
	// if (eachLine.indexOf("package") > -1) {
	// packageInfo = eachLine;
	// break;
	// }
	// }
	// }
	//
	// resultModel.setContents(codeInfo);
	// resultModel.setTitle(file.getCanonicalPath());
	// resultModel.setSDPackageInfo(packageInfo);
	//
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// JARVIS.debug("Unsupported encoding.");
	// e.printStackTrace();
	// } catch (IOException e) {
	// JARVIS.debug("Just IO exception.");
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return resultModel;
	// }

	// TODO: 1. �뜝�룞�삕 subsection�뜝�룞�삕 title �뜝�룞�삕�뜝�룞�삕
	public String extractTitle(String title, String path) {
		String result = "";
		String[] titleArr = title.split(TITLE_DELIM);

		if (titleArr.length > 1) {
			result = titleArr[titleArr.length - 1].trim();
		} else {
			if (JARVIS.isNullOrEmpty(title)) {
				if (path.startsWith("RDML_PAPER_")) {
					result = path.substring(11, path.length() - 4);
				} else if (path.startsWith("RDML_")) {
					result = path.substring(5, path.length() - 6);
				}
			} else {
				result = title.trim();
			}
		}

		return result;
	}

	// TODO: 1. �뜝�뙇�궪�삕�뜝�룞�삕 RD�뜝�룞�삕�뜝�룞�삕 title �뜝�룞�삕�뜝�룞�삕 or �뜝�룞�삕�삻竊먨뜝占� �뜝�룞�삕�뜝�룞�삕�뜝�떦�뙋�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃 �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕
	public String extractProjectName(String title) {
		String[] titleArr = title.split(TITLE_DELIM);

		if (titleArr.length > 1) {
			return titleArr[titleArr.length - 1].trim();
		} else {
			return title.trim();
		}
	}

	public String polishingCDATA(String str) {
		return str.substring(8, str.length() - 2);
	}
}

class RD {
	public int depth;
	public String name;
	private ArrayList<RD> rdList;
	private RD parent;

	public RD(String name, int depth) {
		this.name = name;
		this.depth = depth;
		rdList = new ArrayList<RD>();
	}

	public void setRdList(RD rd) {
		rdList.add(rd);
	}

	public ArrayList<RD> getRdList() {
		return this.rdList;
	}

	/**
	 * @return the parent
	 */
	public RD getParent() {
		return this.parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(RD parent) {
		this.parent = parent;
	}

}
