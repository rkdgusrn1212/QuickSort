import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		System.out.println("재귀호출로 구현한 QuickSort");
		doExp(0);
		System.out.println("\n\n반복문으로 구현한 QuickSort");
		doExp(1);
		System.out.println("\n\n기본 라이브러리 제공 QuickSort");
		doExp(2);
	}
	private static void doExp(int flag){
		long minTime = Long.MAX_VALUE;
		long maxTime = 0;
		long totalTime = 0;
		int epoch = 1000;
		int size = 5000;
		int[] maxArr = new int[size];
		int[] minArr = new int[size];
		for(int k=0;k<epoch; k++) {
			int[] in = new int[size];
			int[] backup = new int[size];
			for(int i=0; i<size; i++) {
				int randInt = (int)(Math.random()*size);
				//System.out.print(randInt+" ");
				in[i] = randInt;
				System.arraycopy(in, 0, backup, 0, size);
			}
			//System.out.println();
			long startTime;
			long endTime;
			if(flag==0) {
				startTime = System.nanoTime();
				quickSortRecursive(in, 0, in.length);
				endTime = System.nanoTime();
			}else if(flag==1){
				startTime = System.nanoTime();
				quickSortLoop(in);
				endTime = System.nanoTime();
			}else {
				startTime = System.nanoTime();
				Arrays.sort(in);
				endTime = System.nanoTime();
			}
			long spendTime = endTime - startTime;
			if(spendTime<minTime) {
				minTime = spendTime;
				minArr = backup;
			}
			if(spendTime>maxTime) {
				maxTime = spendTime;
				maxArr = backup;
			}
			totalTime+=spendTime;
			//for(int i:in) {
			//	System.out.print(i+" ");
			//}
			//System.out.printf("\n걸린시간 : %.8f\n",(endTime-startTime)/1000000000.0);
		}
		System.out.printf("최대시간 : %.8f\n", maxTime/1000000000.0);
		for(int i:maxArr) {
			System.out.print(i+" ");
		}
		System.out.printf("\n최소시간 : %.8f\n", minTime/1000000000.0);
		for(int i:minArr) {
			System.out.print(i+" ");
		}
		System.out.printf("\n평균시간 : %.8f\n", totalTime/epoch/1000000000.0);
	}
	
	//[str, end)범위에서 오름차순 정렬.
	public static void quickSortRecursive(int[] list, int str, int end){
		if(end-str<2) {
			return;//정렬 안해도됨.
		}
		int pivot = str;
		int left = str+1;
		int right = end-1;
		while(true) {
			while(right>=left&&list[left]<list[pivot]) {
				left++;
			}
			while(right>=left&&list[pivot]<=list[right]) {
				right--;
			}
			if(right>=left) {
				int temp = list[left];
				list[left] = list[right];
				list[right] = temp;
				left++;
				right--;
			}else {
				break;
			}
		}
		int temp = list[pivot];
		list[pivot] = list[right];
		list[right] = temp;
		
		quickSortRecursive(list, str, right);
		quickSortRecursive(list, left, end);
	}
	
	private static void quickSortLoop(int[] src) {
		if(src.length>1) {
			//길이를 2이상이라 가정.
			int[][] parts = new int[src.length][2];//각 파티션의 [str, end)범위를 저장
			//파티션 사이에는 만드시 피벗이 존재. 그러면 최대 src.length/2+1개 존재
			//메모리를 매번 할당하는 오버헤드를 방지하기위해 그냥 미리 공간확보를 해놓자.
			parts[0][0] = 0;
			parts[0][1] = src.length;
			int partsLen = 1;
			for(int i =0 ;i< partsLen; i++) {
				
				int pivotIdx = parts[i][0];
				int leftIdx = pivotIdx+1;
				int rightIdx = parts[i][1]-1;
				while(rightIdx>=leftIdx) {
					if(src[leftIdx]<src[pivotIdx]) {
						leftIdx++;//조건이 맞으니 패스
					}else {
						if(src[rightIdx]>=src[pivotIdx]) {//같은거도 위쪽 파티션으로 보내자.
							rightIdx--;//조건이 맞으니 패스.
						}else {
							//둘다 조건이 안맞으면 swap
							int temp = src[leftIdx];
							src[leftIdx] = src[rightIdx];
							src[rightIdx] = temp;
							leftIdx++;
							rightIdx--;
						}
					}
				}
				//무조건 r<l
				//혹여나 겹치더라도 그값이 p보다 같거나크면 r--되고 작으면 l++되기 때문이다.
				//그러면 while문을 탈출한다.
				
				//피벗을 r위치로 swap;
				int temp = src[pivotIdx];
				src[pivotIdx] = src[rightIdx];
				src[rightIdx] = temp;
				//파티션 추가. 2칸이상만 넣음.
				if(pivotIdx <rightIdx-1) {
					parts[partsLen][0] = pivotIdx;
					parts[partsLen++][1] = rightIdx;
				}
				if(rightIdx + 2<parts[i][1]) {
					parts[partsLen][0] = rightIdx + 1;
					parts[partsLen++][1] = parts[i][1];
				}
			}
		}
	}
}
