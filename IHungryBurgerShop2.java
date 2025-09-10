import java.util.*;
class IHungryBurgerShop2{
	public static String[] orderId={"B0001","B0002","B0003","B0004","B0005"};
	public static String[] customerId={"0712345678","0770981234","0724512084","0777891123","0719288377"};
	public static String[] customerName={"Amal","Kamal","Nimali","Lakmali","Ruwan"};
	public static int[] orderQty={2,1,4,2,3};
	public static int[] orderStatus={1,2,1,0,0};

	final static double BURGERPRICE=500;
	
	public static final int PREPARING=0; 
	public static final int DELIVERED=1; 
	public static final int CANCEL=2;
	
	public static void placeOrder(){
		L1:do{
			clearConsole(); 
			Scanner input=new Scanner(System.in);
			System.out.println();
			System.out.println("-------------------------------------------------------------------");
			System.out.println("|                            PLACE ORDER                          |");
			System.out.println("-------------------------------------------------------------------");
			System.out.println();
			String orderID=addOrder();
			System.out.println("ORDER ID - "+orderID);
			System.out.println("======================");
			
			L2:do{
				System.out.println();
				System.out.print("Enter CustomerID (phone no.): ");
				String custID=input.nextLine();	
				if(IsValidCID(custID)==false){
						System.out.println("\tInvalid Customer ID. Try Again...\n");
						continue L2;
				}
				String custName="";
				int index=checkCustID(custID);
				if(index==-1){
					System.out.print("Customer Name    : ");
					custName=input.nextLine();
					
				}else{
					custName=customerName[index];
					System.out.println("Customer Name    : "+custName);
				}
				
					System.out.print("Enter Burger Quantity - ");
					int q=input.nextInt();
					input.nextLine();
					if(q<=0){
						System.out.println("\tInvalid Quantity. Try Again...\n");
						continue L2;
					}
					
					String tot=calcOrderValue(q);
					System.out.println("Total value - "+tot);
					System.out.println();
					int orderSt=0;
					
				while(true){
					System.out.print("\tAre you confirm order (Y/N) - ");
					char result=input.next().charAt(0);
					input.nextLine();
					if(result=='Y'||result=='y'){
						extendArray();
						int size=customerId.length-1;
						orderId[size]=orderID;
						customerId[size]=custID;
						customerName[size]=custName;
						orderQty[size]=q;
						
						System.out.println("\n\tYour order is enter to the system successfully...\n");
						while(true){
							System.out.print("Do you want to place another order (Y/N): ");
							char response=input.next().charAt(0);
							input.nextLine();
							if(response=='Y'||response=='y'){
								
								continue L1;
							}else if(response=='N'||response=='n'){
								return;
							}else{
								System.out.println("\n\tInvalid Option. Try Again...\n");
								
							}
						}
					
						
					}else if(result=='N'||result=='n'){
						
						continue L1;
					}else{
						System.out.println("\n\tInvalid Option. Try Again...\n");
						
					}
				}
			
	
			}while(true);
			
		}while(true);
	}

	public static String addOrder(){
		if(orderId.length==0){
			return "B0001";
		}
		String lastNumber=orderId[orderId.length-1].substring(1);
		return String.format("B%04d",Integer.parseInt(lastNumber)+1);
	}
	public static boolean IsValidCID(String custID){
		if(custID.length()!=10){
			return false;
		}
		if(custID.charAt(0)!='0'){
			return false;
		}
		for(int i=0;i<custID.length();i++){
			if(custID.charAt(i)<48 || custID.charAt(i)>57){
				return false;
			}
		}	
		return true;
	}
	public static int checkCustID(String custID){
		for(int i=0;i<customerId.length;i++){
			if(customerId[i].equalsIgnoreCase(custID)){
				return i;
			}
		}
		return -1;
	
	}
	public static void extendArray(){
		int size=customerId.length;
		String[] tempCustomerId=new String[size+1];
		String[] tempOrderId=new String[size+1];
		String[] tempCustomerName=new String[size+1];
		int[] tempOrderQty=new int[size+1];
		int[] tempOrderStatus=new int[size+1];
		
		for(int i=0; i<size; i++){
			tempOrderId[i]=orderId[i];
			tempCustomerId[i]=customerId[i];
			tempCustomerName[i]=customerName[i];
			tempOrderQty[i]=orderQty[i];
			tempOrderStatus[i]=orderStatus[i];
		}
		orderId=tempOrderId;
		customerId=tempCustomerId;
		customerName=tempCustomerName;
		orderQty=tempOrderQty;
		orderStatus=tempOrderStatus;

	}
	
	public static String getOrderStatus(int orderSt){
		if(orderSt==PREPARING){ 
			return "Preparing";
		}else if(orderSt==DELIVERED){
			return "Delivered";
		}else if(orderSt==CANCEL){
			return "Cancelled";
		}else{
			return "Unknown";
		}
	}


	public static void searchBestCustomer(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                         BEST CUSTOMER                           |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		System.out.println();
		String[] custArr=new String[0];
		
		for(int i=0;i<customerId.length;i++){
			if(!searchCust(custArr,customerId[i])){
				String[] tempArr=new String[custArr.length+1];
				for(int j=0;j<custArr.length;j++){
					tempArr[j]=custArr[j];
				}
				custArr=tempArr;
				custArr[custArr.length-1]=customerId[i];
			}
		}
		int[] total=new int[custArr.length];
		
		for(int i=0;i<custArr.length;i++){
			int tot=0;
			for(int j=0;j<customerId.length;j++){
				if(custArr[i].equalsIgnoreCase(customerId[j])){
					tot+=(orderQty[j]*BURGERPRICE);
				}
			}
			total[i]=tot;
		}
		
		
		for(int i=0;i<custArr.length-1;i++){
			for(int j=0;j<custArr.length-1;j++){
				if(total[j]<total[j+1]){
					int temp=total[j];
					total[j]=total[j+1];
					total[j+1]=temp;
					
					String tempid=custArr[j];
					custArr[j]=custArr[j+1];
					custArr[j+1]=tempid;
				}
			}
		}
		String[] custName=new String[custArr.length];
		
		for(int i=0;i<custArr.length;i++){
			for(int j=0;j<customerId.length;j++){
				if(custArr[i].equalsIgnoreCase(customerId[j])){
					custName[i]=customerName[j];
					
				}
			}
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Customer ID\tName\t\tTotal");
		System.out.println("-------------------------------------------");
		for(int i=0;i<custArr.length;i++){
			System.out.printf("%s\t%s\t\t%.2f\n", custArr[i], custName[i], (double)total[i]);
			System.out.println("-------------------------------------------");
		}
		
		while(true){
			System.out.print("\n\n\tDo you want to go back to main menu? (Y/N) - ");
			char opt=input.next().charAt(0);
			if(opt=='y'||opt=='Y'){
				return;
			}else if(opt=='N'||opt=='n'){
				searchBestCustomer();
				return;
			}else{
				System.out.println("\n\tInvalid Option...");
			}
		}

	}
	public static boolean searchCust(String[] arr,String a){
		for(int i=0;i<arr.length;i++){
			if(arr[i].equalsIgnoreCase(a)){
				return true;
			}
		}
		return false;
	}
	public static void searchOrder(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                      SEARCH ORDER DETAILS                       |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		System.out.print("Enter order ID - ");
		String oID=input.nextLine();
		System.out.println();
		int i=indexOfOrder(oID);
		if(i!=-1){
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("OrderID\t\tCustomerID\tName\tQuantity\tOrderValue\tOrderStatus|");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println(orderId[i]+"\t\t"+customerId[i]+"\t"+customerName[i]+"\t"+orderQty[i]+"\t\t"+ calcOrderValue(orderQty[i])+"\t\t"+getOrderStatus(orderStatus[i])+"  |");
			System.out.println("------------------------------------------------------------------------------------\n");
			
			while(true){
				System.out.print("Do you want to search another order details (Y/N): ");
				char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					searchOrder();
					return;
				}else if(op=='N'||op=='n'){
					return;
				}else{
					System.out.println("\n\tInvalid Option...");
				}
			}
		}else{
			while(true){
				System.out.print("\nInvalid Order ID. Do you want to enter again? (Y/N)> ");
				char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					searchOrder();
					return;
				}else if(op=='N'||op=='n'){
					return;
				}else{
					System.out.println("\n\tInvalid Option...");
				}
			}
		}

	}
	public static int indexOfOrder(String oID){
		for(int i=0;i<orderId.length;i++){
			if(oID.equalsIgnoreCase(orderId[i])){
				return i;
			}
		}
		return -1;
	}
	
	public static void searchCustomer(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                    SEARCH CUSTOMER DETAILS                      |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		while(true){
			System.out.print("Enter customer ID - ");
			String cID=input.nextLine();
			System.out.println();
			if(IsValidCID(cID)==false){
				System.out.println("\n\tInvalid Customer ID. Try Again...\n");
				continue;
			}else{
				int i=checkCustID(cID);
				if(i==-1){
					System.out.println("\n\tThis customer ID is not added yet...");
				}else{
					System.out.println("CustomerID - "+customerId[i]);
					System.out.println("Name       - "+customerName[i]);
					System.out.println("\nCustomer Order Details");
					System.out.println("=========================\n\n");
					System.out.println("------------------------------------------------------");
					System.out.println("Order_ID\tOrder_Quantity\t\tTotal_Value");
					System.out.println("------------------------------------------------------");
					for(int j=0;j<customerId.length;j++){
						if(cID.equalsIgnoreCase(customerId[j])){
							System.out.println(orderId[j]+"\t\t"+orderQty[j]+"\t\t\t"+calcOrderValue(orderQty[j]));
						}
					}
					System.out.println("------------------------------------------------------");
				}
			}
			while(true){
				System.out.print("\nDo you want to search another customer details (Y/N): ");
				char op=input.next().charAt(0);
					if(op=='Y'||op=='y'){
						searchCustomer();
						return;
					}else if(op=='N'||op=='n'){
						return;
					}else{
						System.out.println("\n\tInvalid Option...");
					}
			}
		}
	}
	public static String calcOrderValue(int quantity){
		double total=BURGERPRICE*quantity;
		return String.format("%.2f", total); 
	}

	
	public static void viewOrder(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                       VIEW ORDER LIST                           |");
		System.out.println("-------------------------------------------------------------------");
	
		System.out.println();
		System.out.println("[1] Delivered Order");
		System.out.println("[2] Preparing Order");
		System.out.println("[3] Cancel Order");
		while(true){  
			System.out.println();
			System.out.print("Enter an option to continue > ");
			int op=input.nextInt();
			input.nextLine();
			switch(op){
				case 1:
					deliveredOrder();
					return;
				case 2:
					preparingOrder();
					return;
				case 3:
					cancelOrder();
					return;
				default:
					System.out.println("\n\tInvalid Option. Try Again...");
				}
			
			
		}
			
		
	}
	public static void deliveredOrder(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                       DELIVERED ORDER                           |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("OrderID\t\tCustomerID\tName\t\tQuantity\tOrderValue");
		System.out.println("----------------------------------------------------------------------------");
		for(int i=0;i<orderStatus.length;i++){	
			if(orderStatus[i]==1){
				System.out.println(orderId[i]+"\t\t"+customerId[i]+"\t"+customerName[i]+"\t\t"+orderQty[i]+"\t\t"+ calcOrderValue(orderQty[i]));
				System.out.println("----------------------------------------------------------------------------");
			}
		}
		while(true){
			System.out.println();
			System.out.print("Do you want to go to home page (Y/N): ");
			char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					return;
				}else if(op=='N'||op=='n'){
					deliveredOrder();
					return;
				}else{
					System.out.println("\n\tInvalid Option...");
				}
			}
		
				

	}
	public static void preparingOrder(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                       PREPARING ORDER                           |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("OrderID\t\tCustomerID\tName\t\tQuantity\tOrderValue");
		System.out.println("----------------------------------------------------------------------------");
		for(int i=0;i<orderStatus.length;i++){	
			if(orderStatus[i]==0){
				System.out.println(orderId[i]+"\t\t"+customerId[i]+"\t"+customerName[i]+"\t\t"+orderQty[i]+"\t\t"+ calcOrderValue(orderQty[i]));
				System.out.println("----------------------------------------------------------------------------");
			}
		}
		while(true){
			System.out.println();
			System.out.print("Do you want to go to home page (Y/N): ");
			char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					return;
				}else if(op=='N'||op=='n'){
					preparingOrder();
					return;
				}else{
					System.out.println("\n\tInvalid Option...");
				}
			}
		

	}
	public static void cancelOrder(){
		clearConsole(); 
		Scanner input=new Scanner(System.in);
		System.out.println();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("|                       CANCEL ORDER                              |");
		System.out.println("-------------------------------------------------------------------");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("OrderID\t\tCustomerID\tName\t\tQuantity\tOrderValue");
		System.out.println("----------------------------------------------------------------------------");
		for(int i=0;i<orderStatus.length;i++){	
			if(orderStatus[i]==2){
				System.out.println(orderId[i]+"\t\t"+customerId[i]+"\t"+customerName[i]+"\t\t"+orderQty[i]+"\t\t"+ calcOrderValue(orderQty[i]));
				System.out.println("----------------------------------------------------------------------------");
			}
		}
		while(true){
			System.out.println();
			System.out.print("Do you want to go to home page (Y/N): ");
			char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					return;
				}else if(op=='N'||op=='n'){
					cancelOrder();
					return;
				}else{
					System.out.println("\n\tInvalid Option...");
				}
			}
	}
	
	public static void updateOrderDetails(){
		L10:while(true){
			clearConsole(); 
			Scanner input=new Scanner(System.in);
			System.out.println();
			System.out.println("-------------------------------------------------------------------");
			System.out.println("|                     UPDATE ORDER DETAILS                        |");
			System.out.println("-------------------------------------------------------------------");
			L9:while(true){
				System.out.println();
				System.out.print("Enter order ID - ");
				String odID=input.nextLine();
				System.out.println();
				int index=indexOfOrder(odID);
				if(index!=-1){
					if(orderStatus[index]==1){
						System.out.println("This Order is already delivered...You cannot update this order...");
					}else if(orderStatus[index]==2){
						System.out.println("This Order is already cancelled...You cannot update this order...");
					}else{
						System.out.println("OrderID    - "+orderId[index]);
						System.out.println("CustomerID - "+customerId[index]);
						System.out.println("Name       - "+customerName[index]);
						System.out.println("Quantity   - "+orderQty[index]);
						System.out.println("OrderValue - "+calcOrderValue(orderQty[index]));
						System.out.println("OrderStatus- "+getOrderStatus(orderStatus[index]));
						System.out.println();
						System.out.println("What do you want to update ?");
						System.out.println("\t(1) Quantity");
						System.out.println("\t(2) Status");
						L8:while(true){
							System.out.println();
							System.out.print("Enter your option - ");
							int op=input.nextInt();
							input.nextLine();
							switch(op){
								case 1:
									updateQuantity(index);
									break L8;
								case 2:
									updateStatus(index);
									break L8;
								default:
									System.out.println("\n\tInvalid Option...");
							}
						}	
					}
					l7:while(true){
						System.out.println();
						System.out.print("\nDo you want to update another order details (Y/N): ");
						char op=input.next().charAt(0);
						input.nextLine();
						if(op=='Y'||op=='y'){
							continue L10;
							
						}else if(op=='N'||op=='n'){
							return;
							
						}else{
							System.out.println("\n\tInvalid Option...");
						}
				}
				}else{
					System.out.print("\nInvalid Order ID.");
					while(true){
						System.out.print("Do you want to enter again? (Y/N)> ");
						char op=input.next().charAt(0);
						input.nextLine();
						if(op=='Y'||op=='y'){
							continue L10;
						}else if(op=='N'||op=='n'){
							return;
						}else{
							System.out.println("\n\tInvalid Option...\n");
						}
					}
				}	
			}
		}
	}
	public static void updateQuantity(int index){
		Scanner input=new Scanner(System.in);
		clearConsole();
		System.out.println("Quantity Update");
		System.out.println("================\n");
		System.out.println("OrderID    - "+orderId[index]);
		System.out.println("CustomerID - "+customerId[index]);
		System.out.println("Name       - "+customerName[index]);
		while(true){
			System.out.println();
			System.out.print("Enter your quantity update value - ");
			int qty=input.nextInt();
			input.nextLine();
			if(qty>0){
				orderQty[index]=qty;
				System.out.println("\n\tUpdate order quantity successfully...");
				System.out.println();
				System.out.println("New order quantity - "+orderQty[index]);
				System.out.println("New order value - "+calcOrderValue(orderQty[index]));
				break;
			}else{
				System.out.println("\n\tInvalid quantity...");
			}
		}
				
		
	}
	public static void updateStatus(int index){
		Scanner input=new Scanner(System.in);
		clearConsole();
		System.out.println("Status Update");
		System.out.println("================\n");
		System.out.println("OrderID    - "+orderId[index]);
		System.out.println("CustomerID - "+customerId[index]);
		System.out.println("Name       - "+customerName[index]);
		System.out.println();
		
		System.out.println("\t(0) Preparing");
		System.out.println("\t(1) Delivered");
		System.out.println("\t(2) Cancel");
		System.out.println();
		System.out.print("Enter new order status - ");
		int sts=input.nextInt();
		input.nextLine();
		orderStatus[index]=sts;
		System.out.println("\n\tUpdate order status successfully...");
		System.out.println();
		System.out.println("New order status - "+getOrderStatus(orderStatus[index]));
	}
		
		
	
	
	public static void exit(){ 
		clearConsole(); 
		System.out.println("\n\t\tYou left the program...\n"); 
		System.exit(0); 
	}
	public final static void clearConsole(){ 
		try { 
			final String os = 
			System.getProperty("os.name"); if 
			(os.contains("Windows")) { 
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); 
			} else { 
				System.out.print("\033[H\033[2J"); 
				System.out.flush(); 
			} 
		} catch (final Exception e) { 
			e.printStackTrace(); 
			 
		} 
	} 
	
	
	public static void main(String args[]){
		Scanner input=new Scanner(System.in);
		do{
			clearConsole();
			System.out.println("-------------------------------------------------------------------");
			System.out.println("|                         iHungry Burger                          |");
			System.out.println("-------------------------------------------------------------------");
			System.out.println();
			System.out.println("[1] Place Order                     [2] Search Best Customer");
			System.out.println("[3] Search Order                    [4] Search Customer");
			System.out.println("[5] View Orders                     [6] Update Order Details");
			System.out.println("[7] Exit");
			System.out.println();
			System.out.print("Enter an option to continue >");
			int op=input.nextInt();
			input.nextLine();
			switch(op){
				case 1:
					placeOrder();
					break;
				case 2:
					searchBestCustomer();
					break;
				case 3:
					searchOrder();
					break;
				case 4:
					searchCustomer();
					break;
				case 5:
					viewOrder();
					break;
				case 6:
					updateOrderDetails();
					break;
				case 7:
					exit();
					break;
				default :
					System.out.println("\n\tInvalid Option. Try Again...");
			}
		}while(true);
		
		
		
	}
}

