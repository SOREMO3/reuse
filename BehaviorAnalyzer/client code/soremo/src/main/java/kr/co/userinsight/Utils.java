package kr.co.userinsight;

import java.util.ArrayList;

import kr.co.userinsight.model.RDStructureModel;

public class Utils {
	
	public static RDStructureModel rdParentAlgorithm(ArrayList<RDStructureModel> rdList){
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
		return rdList.get(0);
	}
	
	public static RDStructureModel rdBaseSearchAlgorithm(RDStructureModel rootRD, String baseRDID){ /////// �����ؾ���
		RDStructureModel rdPointer = rootRD;
		RDStructureModel rdPointer2 = null;
		
		if(rootRD.getId().equals(baseRDID)){
			return rootRD;
		}
//		else if(!rootRD.getId().equals(baseRDID) && rootRD.getNestedRD() == null){
//			return null;
//		}
		
		for(int i=0; i<rootRD.getNestedRD().size(); i++){
			
			//System.out.println("������ �κ��� rdPointer��? " + rdPointer);
			
			if(rdPointer == null)
				return null;
			
			if(!rdPointer.getNestedRD().isEmpty()){ 
				rdPointer2 = rdPointer;
				System.out.println("QQQQQQQQQQ : " + rdPointer.getId());
				rdPointer = rdBaseSearchAlgorithm(rdPointer.getNestedRD().get(i), baseRDID);
				
//				if(rdPointer == null){
//					System.out.println("��������");
//					rdPointer2 = rdBaseSearchAlgorithm(rdPointer2.getNestedRD().get(i), baseRDID);
//				}
				if(rdPointer != null && !rdPointer.getId().equals(rootRD.getId())){
					System.out.println("WWWWWWWWWW : " + rdPointer.getId());
					return rdPointer;
				}
			}
			else if(rdPointer.getNestedRD().isEmpty() && rdPointer.getId().equals(rootRD.getId())){
				System.out.println("??? : " + rdPointer.getId());
				return rdPointer;
			}
			else{
				System.out.println("��������1");
				return rdPointer.getParent().getNestedRD().get(rdPointer.getParent().getNestedRD().indexOf(rdPointer)+1);
			} 
		}
		
		return null;
	}
	
	public static RDStructureModel rdEndSearchAlgorithm(RDStructureModel baseRD){
		
		RDStructureModel rdPointer = baseRD;

		for (int i = 0; i < baseRD.getNestedRD().size(); i++) {
			if(rdPointer.getNestedRD().isEmpty() 
					&& rdPointer.getParent().getParent().getNestedRD().getLast() == rdPointer.getParent()
					&& rdPointer.getParent().getNestedRD().getLast() == rdPointer){
				System.out.println("this is end " + rdPointer.getId());
				return rdPointer;
			}
			else{
				System.out.println("������: " + rdPointer.getId());
				
				if(rdPointer.getParent() == null){
					rdPointer = rdEndSearchAlgorithm(rdPointer.getNestedRD().get(rdPointer.getNestedRD().indexOf(rdPointer)+1));
					if(rdPointer != null)
						return rdPointer;
				}
				else{
					if(rdPointer.getParent().getNestedRD().getLast() == rdPointer)
					{
						rdPointer = rdEndSearchAlgorithm(rdPointer.getNestedRD().get(rdPointer.getNestedRD().indexOf(rdPointer)+1));
						if(rdPointer != null)
							return rdPointer;
					}
					else{
						rdPointer = rdEndSearchAlgorithm(rdPointer.getParent().getNestedRD().get(rdPointer.getParent().getNestedRD().indexOf(rdPointer)+1));
						if(rdPointer != null)
							return rdPointer;
					}
				}
			}
		}
		//System.out.println(rdPointer.getParent().getNestedRD().get(rdPointer.getParent().getNestedRD().indexOf(rdPointer)+1).getId());
		if(rdPointer != null)
			return rdPointer;
		
		return null;
	}
	
	// TODO: rdBaseSearchAlgorithm �� IntraNext ����
	public static RDStructureModel IntraNext( RDStructureModel _rdStructureModel, RDStructureModel baseRD){
		
		RDStructureModel rdResult = null;
		RDStructureModel rdPointer = null;
		RDStructureModel rdEndPointer = null;
		
		
		rdEndPointer = rdEndSearchAlgorithm(_rdStructureModel); // ������ ���������
		//baseRD = rdBaseSearchAlgorithm(_rdStructureModel,_tarRD); // reuse base rd ���
		
		rdPointer = baseRD;
		System.out.println("������ ������ ����?? => " + rdEndPointer.getId());
			
		for(int i=0; i<= _rdStructureModel.getNestedRD().size(); i++){
			if(rdPointer.getId() == rdEndPointer.getId()){
				System.out.println("1");
				return null;
			}
			// TODO ����
			
			if(rdPointer.getNestedRD().isEmpty()){ // �ڽ��� ����
				System.out.println("3++++++++++++ : " + rdPointer.getParent().getNestedRD().getLast().getId());
				if(rdPointer.getParent().getNestedRD().getLast().getId() == rdPointer.getId()){ // ������ ������
					System.out.println("4+++++++++ : " + rdPointer.getId());
					return SearchNextSiblingNode(rdPointer);
				}
				else{ // ������ �ƴϸ� ���� ������ �Ѿ
					return rdPointer.getParent().getNestedRD().get(rdPointer.getParent().getNestedRD().indexOf(rdPointer)+1);
				}
			}
			
//			if(rdPointer.getParent() == null){
//				System.out.println("??? : " + rdPointer.getId() + " ??!!: " + rdPointer.getNestedRD().get(0).getId());
//				rdResult = rdPointer.getNestedRD().get(0);
//			}
//			else if(rdPointer.getParent().getNestedRD().getLast().getId() != rdPointer.getId()){
//				System.out.println("4+++++++++ rdStructureModel.getNestedRD().empty()");
//				rdPointer = SearchNextSiblingNode(rdPointer);
//				if(rdPointer.getNestedRD().isEmpty()){
//					return rdPointer;
//				}
//				else{
//					rdPointer = IntraNext(_rdStructureModel, rdPointer);
//					if(rdPointer != null)
//						return rdPointer;
//				}
//			}
			else{
				System.out.println("2+++++++++ : " + rdPointer.getId() + "    " + rdPointer.getNestedRD().get(0).getId());
				rdResult = rdPointer.getNestedRD().get(0);
			}
			
//			if(rdPointer.getNestedRD() == null && rdPointer.getId() == rdPointer.getParent().getNestedRD().getLast().getId()){
//				System.out.println("4+++++++++ rdStructureModel.getNestedRD() == null");
//				return SearchNextSiblingNode(rdPointer);
//				//rdResult = IntraNext(rdPointer.getNestedRD().get(i), baseRD);
//			}
		}
		
		return rdResult;
	}
	
	public static RDStructureModel SearchNextSiblingNode(RDStructureModel _rdPointer){
		//RDStructureModel rdStructureModel = _rdStructureModel;
		RDStructureModel rdPointer = _rdPointer;
		
		if(rdPointer.getId() == rdPointer.getParent().getNestedRD().getLast().getId()){
			System.out.println("2-1: " + rdPointer.getId());
			return SearchNextSiblingNode(rdPointer.getParent());
		}
		else{
			System.out.println("2-2: " + rdPointer.getId());
			return rdPointer.getParent().getNestedRD().get(rdPointer.getParent().getNestedRD().indexOf(rdPointer)+1);
		}
	}

	public static RDStructureModel IntraPrevious(RDStructureModel _rdStructureModel, boolean flag, String _tarRD, String rootID){
		
		RDStructureModel rdStructureModel = _rdStructureModel;
		
		RDStructureModel rdPointer = null;
		
		if(rdStructureModel.getId() == rootID){
			
			return null;
		}
		
		if(flag == true){
			
			try{
				//System.out.println("2-111: " + rdStructureModel.getId());
				//System.out.println("2-333: " + rdStructureModel.getNestedRD().getLast().getId());
				//System.out.println("2-333: " + rdStructureModel.getNestedRD().getLast().getNestedRD().isEmpty());
				if(rdStructureModel.getNestedRD().getLast().getNestedRD().isEmpty()){
					//System.out.println("2-222");
					Exception e = new Exception("������");
					throw e;
				}
			}catch(Exception e){
				//System.out.println("2-1..... 16���� 15���� ����");
				flag = false;
			}finally{
				//System.out.println("2-2..... 15���� 14���� ����");
				rdPointer = rdStructureModel;
				
			}

			if(rdPointer != null && flag == true){
				//System.out.println("aaaaa1: " + rdPointer.getId());
				return rdPointer; // 15���� 14�� ����
			}
			
			if(rdPointer != null && flag == false){ // 16���� 15�ΰ���
				//System.out.println("aaaaa2: " + rdPointer.getId());
				return rdPointer.getParent().getNestedRD().getLast();
			}
			
			if(rdStructureModel.getNestedRD() != null){
					//System.out.println("2-2: " + rdStructureModel.getId());
					if(rdStructureModel.getNestedRD() == null){
						//System.out.println("���");
						return rdStructureModel;
					}
					rdPointer = IntraPrevious(rdStructureModel.getNestedRD().getLast(), flag, _tarRD, rootID);
					if(rdPointer != null)
						return rdPointer;
			}
			//System.out.println("4444444");
		}
		
		
		if(!rdStructureModel.getNestedRD().isEmpty()){
			for(int i=0; i<rdStructureModel.getNestedRD().size(); i++){
				//System.out.println(rdStructureModel.getNestedRD().get(i).getId() + "   " + _tarRD);
								
				
				if(rdStructureModel.getNestedRD().get(i).getId().equals(_tarRD)){
					
					if(i != 0){ // ù��° �ڽ� �ƴϿ���
						//�ٷξ� ������ ���� �ڽ��� ���� �ڽĵ��� �˻�	
						System.out.println("1111111");
						flag = true;
						if(rdStructureModel.getNestedRD().get(0).getNestedRD().isEmpty())
							return rdStructureModel.getNestedRD().get(0);
						rdPointer = IntraPrevious(rdStructureModel.getNestedRD().get(0).getNestedRD().getLast(),flag, _tarRD, rootID);
//						rdPointer = IntraPrevious(rdStructureModel.getParent().getNestedRD().get(rdStructureModel.getParent().getNestedRD().indexOf(rdStructureModel)-1),flag, _tarRD, rootID);
						if(rdPointer != null)
							return rdPointer;
					}
					else {// ù��° �ڽ� 
						if(rdStructureModel.getParent() != null){
							System.out.println("2222222: " );
							if(rdStructureModel.getParent().getNestedRD().size() != 1){
								System.out.println("3333333: " );
								System.out.println("2: " +rdStructureModel.getId());
								flag = true;
								
								if(rdStructureModel.getParent().getNestedRD().indexOf(rdStructureModel) == 0){
									rdPointer = IntraPrevious(rdStructureModel.getParent().getNestedRD().get(rdStructureModel.getParent().getNestedRD().indexOf(rdStructureModel)),flag, _tarRD, rootID);
								}
								else{
									rdPointer = IntraPrevious(rdStructureModel.getParent().getNestedRD().get(rdStructureModel.getParent().getNestedRD().indexOf(rdStructureModel)-1),flag, _tarRD, rootID);
									//rdPointer = IntraPrevious(rdStructureModel.getParent(),flag, _tarRD, rootID);
									if(rdPointer != null)
										return rdPointer;
								}
							}
						}
						
						return rdStructureModel;
					}
				}
				else{
					System.out.println("3: " + rdStructureModel.getId());
					rdPointer = IntraPrevious(rdStructureModel.getNestedRD().get(i),flag, _tarRD, rootID);
					if(rdPointer != null)
						return rdPointer;
				}
			}
		}
		
		
		System.out.println("5: " + rdStructureModel.getId());
		return rdPointer;
	}
}
