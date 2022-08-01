public class QuickSort {

	public static void main(String[] args) {
		long minTime = Long.MAX_VALUE;
		long maxTime = 0;
		long totalTime = 0;
		int[] maxArr = new int[100];
		int[] minArr = new int[100];
		for(int k=0;k<1000; k++) {
			int[] in = new int[100];
			int[] backup = new int[100];
			for(int i=0; i<100; i++) {
				int randInt = (int)(Math.random()*100);
				//System.out.print(randInt+" ");
				in[i] = randInt;
				System.arraycopy(in, 0, backup, 0, 100);
			}
			//System.out.println();
			long startTime = System.nanoTime();
			quickSortRecursive(in, 0, in.length);
			long endTime = System.nanoTime();
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
		System.out.printf("\n평균시간 : %.8f\n", totalTime/100/1000000000.0);
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
}
