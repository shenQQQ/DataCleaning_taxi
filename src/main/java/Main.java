
import utils.OutputUtils;

import java.util.concurrent.*;


public class Main {
    static int count = 0;
//    static int[] array = new int [10];
    //零时写一个车辆id的数组
    static int [] array={2,6,8,19,23,27,37,40,46,47};
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        for(int i=0;i< array.length;i++){    //数组初始化，元素为所有车辆的ID：[0,119]
//            //arr[54] = 54 之前的就直接跳过去了
//            array[i]=i;
//        }
        System.out.println("本次队列共计" + array.length + "个元素");
        ThreadPoolExecutor pool = new ThreadPoolExecutor(7, 11, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(12000));
        //ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < array.length; i++) {
            for (int j = i+1; j < array.length; j++) {
                count++;
                int id1 = array[i];
                int id2 = array[j];
                MyRunnable myThread = new MyRunnable(id1, id2, count, startTime);
                pool.execute(myThread);
                //OutputUtils  utils = new OutputUtils();
                //utils.outputResult(id1,id2);
            }
        }
        pool.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("共计运行时间：" + (endTime - startTime) + "ms");
    }
}

class MyRunnable implements Runnable {
    private int id1;
    private int id2;
    private int i;
    private long startTime;

    MyRunnable(int id1, int id2, int count, long startTime) {
        this.id1 = id1;
        this.id2 = id2;
        this.i = count;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        System.out.println("第" + i + "条线程开始执行");
        OutputUtils utils = new OutputUtils();
        utils.outputResult(id1, id2);
        System.out.println("第" + i + "条线程执行结束");
        long endTime = System.currentTimeMillis();
        System.out.println("共计运行时间：" + (endTime - startTime) + "ms");
    }
}

