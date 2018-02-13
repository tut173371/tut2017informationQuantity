
package s4.b173371; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;


public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int [] suffixArray;
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a interger, which is the starting position in mySpace. // The following is the code to print the variable
    private void printSuffixArray() {
        if(spaceReady) {
            for(int i=0; i< mySpace.length; i++) {
                int s = suffixArray[i];
                for(int j=s;j<mySpace.length;j++) {
                    System.out.write(mySpace[j]); }
                System.out.write('\n'); }
        }
    }
    //文字列比較
    private int suffixCompare(int i, int j) {
        // comparing two suffixes by dictionary order.
        // i and j denoetes suffix_i, and suffix_j
        // if suffix_i > suffix_j, it returns 1
        // if suffix_i < suffix_j, it returns -1
        // if suffix_i = suffix_j, it returns 0;
        // It is not implemented yet,
        // It should be used to create suffix array.
        // Example of dictionary order
        // "i"< "o"  : compare by code
        // "Hi"< "Ho"  ; if head is same, compare the next element
        // "Ho"< "Ho "  ; if the prefix is identical, longer string is big
        
        int si = suffixArray[i];
        int sj = suffixArray[j];
        int s = 0;
        if(si>s)s=si;
        if(sj>s)s=sj;
        int n=mySpace.length -s;
        for(int  k=0;k<n;k++){
            if(mySpace[si+k]>mySpace[sj+k]) return 1;
            if(mySpace[si+k]<mySpace[sj+k]) return -1;
        }
        if(si<sj) return 1;
        if(si>sj) return -1;
        
        
        
        
        
        return 0;
    }
    
    //クイックソート関数
    public void QuickSort(int[] suffixArray, int left, int right){
        int curleft = left;
        int curright = right;
        int pivot = (curleft + curright) / 2;
        
        do{
            while(suffixCompare(pivot,curleft)>0){
                
                curleft++;
            }
            
            while(suffixCompare(pivot,curright)<0){
                curright--;
            }
            if(curleft <= curright){
                if(curright == pivot){
                    //ピボットとcurrightが同じ場所を指した時にピボットも動かす
                    pivot = curleft;
                    swap(suffixArray,curleft++,curright--);
                }else if(curleft == pivot){
                    //ピボットとcurleftが同じ場所を指した時にピボットも動かす
                    pivot = curright;
                    swap(suffixArray,curleft++,curright--);
                }else{
                swap(suffixArray, curleft++, curright--);
                }
            }

            
        }while(curleft<=curright);
        //pivotを基準とした左側と右側それぞれで再帰的にクイックソート関数を呼び出す
        if(left < curright){
            QuickSort(suffixArray, left, curright);
        }
        if(curleft < right){
            QuickSort(suffixArray, curleft, right);
        }
    }
    //選択された二つのデータを入れ替える関数
    public void swap(int[] suffixArray, int i, int j){
        
        int value = suffixArray[i];
        suffixArray[i] = suffixArray[j];
        suffixArray[j] = value;
        
      
    }
    
    public void setSpace(byte []space) {
        mySpace = space;
        if(mySpace.length>0){spaceReady = true;}
        else{return;}
    
        suffixArray = new int[space.length];
        // put all suffixes in suffixArray. Each suffix is expressed by one interger.
        for(int i = 0; i< space.length; i++) {
            suffixArray[i] = i;
        }
        
        QuickSort(suffixArray,0,mySpace.length - 1);
        
        
        // Sorting is not implmented yet.
        /* Example from "Hi Ho Hi Ho"
         0: Hi Ho
         1: Ho
         2: Ho Hi Ho
         3:Hi Ho
         4:Hi Ho Hi Ho
         5:Ho
         6:Ho Hi Ho
         7:i Ho
         8:i Ho Hi Ho
         9:o
         A:o Hi Ho
         */
        //printSuffixArray();
    }
    
    //Targetとして与えられた文字列とsuffixArrayで分けられた部分文字列の比較
    private int targetCompare(int i, int start, int end) {
        // It is called from subBytesStarIndex, adn subBytesEndIndex.
        // "start" and "end" are same as in subByteStartIndex, and subByteEndIndex ** // target_start_end is subBytes(start, end) of target **
        // comparing suffix_i and target_start_end by dictonary order with limitation of length; ***
        // if the beginning of suffix_i matches target_start_end, and suffix is longerthan target it returns 0;
        // if suffix_i > target_start_end it return 1; **
        // if suffix_i < target_start_end it return -1 **
        
        // It should be used to search the apropriate index of some suffix.
        // Example of search
        
        // suffix target
        // "o" > i
        // "o" < z
        // "o" = o
        // "o" < oo
        // "Ho" > Hi
        // "Ho" < Hz
        // "Ho" = Ho
        //"Ho"  < "Ho "
        // "Ho" = "H"
        
        
        int si = mySpace.length-suffixArray[i]; //suffixArrayの部分文字列の長さ
        int sj = end;                           //targetの長さ
        int s=0;
        if(si>sj)s=sj;
        else s=si;
        //部分文字列とtargetを1文字ずつ比較
        for(int  k=0;k<s;k++){
            
            if(mySpace[suffixArray[i]+k]>myTarget[k]) return -1;
            if(mySpace[suffixArray[i]+k]<myTarget[k]) return 1;
        }
        if((si>sj) || (si==sj)){
            return 0;//targetのほうが短い場合辞書的に小さい
        }
        else if(si<sj)
            return 1;//targetのほうが長い場合辞書的に大きい
        
        
        return 0;
        
    }
    //binary search関数 二分探索法関数
    private int binarySearch(int start, int end){
        
        int left = 0;
        int right = suffixArray.length - 1;
        int middle = 0;
        
        while(left<=right){
            middle = (left + right) / 2;
            
            if(targetCompare(middle,start,end)==0){
                //targetとmiddleが等しい→見つけたことと同義
                return middle;
            }else if(targetCompare(middle,start,end)>0){
               
                //targetよりmiddleのほうが大きい時,middle以上のデータを棄却
                left = middle + 1;
            }else if(targetCompare(middle,start,end)<0){
                //targeよりmiddleのほうが小さい時,middle以下のデータを棄却
                right = middle - 1;
            }
        }
        
        return suffixArray.length;
        
    }
    
    
    
    private int subByteStartIndex(int start, int end) {
        // It returns the index of the first suffix which is equal or greater than subBytes; // not implemented yet;
        // For "Ho", it will return 5 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
        
        
        
        int value = 0;
        
        
        value = binarySearch(start,end);
        if(value == suffixArray.length){
            return suffixArray.length;
        }
        boolean flag = true;
        while(flag == true){
            //suffixの引数が0を下回ってはならない
            if((value-1) >0){
                //見つけたvalueの位置からstartまで遡る
                if(targetCompare(value-1,start,end)==0){
                    
                    value--;
                }
                else {
                    return value;
                }
            }else{
                
                return value;
            }
        }
        
        //見つからなかった場合はsuffixArrayの長さを返す
        return suffixArray.length;
    }
    
    
    private int subByteEndIndex(int start, int end) {
        // It returns the next index of the first suffix which is greater than subBytes; // not implemented yet
        // For "Ho", it will return 7 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
        
        
        int value = 0;
        value = binarySearch(start,end);
        if(value == suffixArray.length){
            return suffixArray.length;
        }
        boolean flag = true;
        while(flag == true){
            //suffixの引数がsuffixArayの長さを超えてはならない
            if((value+1) < suffixArray.length){
                //見つけた場合valueの位置からendの位置まで登る
                if(targetCompare(value+1,start,end)==0){
                    value++;
                }
                else {
                    return value+1;
                }
            }else{
                
                return value+1;
            }
        }
        //見つからなかった場合はsuffixArrayの長さを返す
        return suffixArray.length;
    }
    
    
    public int subByteFrequency(int start, int end) {
        // This method could be defined as follows though it is slow.
        
        int spaceLength = mySpace.length;
        int count = 0;
        for(int offset = 0; offset< spaceLength - (end - start); offset++) {
            boolean abort = false;
            for(int i = 0; i< (end - start); i++) {
                if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
            if(abort == false) { count++; } }
        
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start, end);
        //inspection code
        /*for(int k=start;k<end;k++)
         { System.out.write(myTarget[k]); }
        System.out.printf(": first=%d last1=%d\n", first, last1);
        */
        if(first == last1){
            return 0;
        }else{
            return last1 - first;
        }
        
    }
    
    
    public void setTarget(byte [] target) {
        myTarget = target;
        if(myTarget.length>0) targetReady = true; }
    
    public int frequency() {
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
        
        
    }
    public static void main(String[] args) {
        Frequencer frequencerObject;
        try {
            frequencerObject = new Frequencer();
            frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            
             frequencerObject.setTarget("j".getBytes());
            
             int result = frequencerObject.frequency();
             
             System.out.print("Freq = "+ result+" ");
             if(1 == result) { System.out.println("OK"); }
             else {System.out.println("WRONG"); }
            
        }
        catch(Exception e) {
            System.out.println("STOP"); }
    }
}
