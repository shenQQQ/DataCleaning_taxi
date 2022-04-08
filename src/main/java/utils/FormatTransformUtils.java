package utils;

import java.io.*;

public class FormatTransformUtils {

    static String titleFilter = "方向,时间";
    static String pre = "D:\\dataFile/car_data/";
    static String suf = ".txt";
    static String[] txtFile = {
            "20210904_data_00",
            "20210904_data_01",
            "20210904_data_02",
            "20210904_data_03",
            "20210904_data_04",
            "20210904_data_05",
            "20210904_data_06",
            "20210904_data_07",
            "20210904_data_08",
            "20210904_data_09",
            "20210904_data_10",
            "20210904_data_11",
            "20210904_data_12",
            "20210904_data_13",
            "20210904_data_14",
            "20210904_data_15",
            "20210904_data_16",
            "20210904_data_17",
            "20210904_data_18",
            "20210904_data_19",
            "20210904_data_20",
            "20210904_data_21",
            "20210904_data_22",
            "20210904_data_23",
    };
    static String txtSplitBy = ",";

    static long titleCount = 0;
    static long lineCount = 0;
    static long totalCount = 0;

    public static void main(String[] args) {
        try {

            for (String file : txtFile) {
                File txtOutput = new File("D:\\dataFile/result/" + file + "_ouput.sql"); // sql数据文件
                System.out.println(txtOutput);
                file = pre + file + suf;
                BufferedWriter bw = new BufferedWriter(new FileWriter(txtOutput, false)); // 不附加
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                    bw.write("use car_data;");
                    bw.newLine();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                            continue;
                        }
                        if (lineCount == 0) {
                            System.out.println("过滤成功");
                            lineCount++;
                            titleCount++;
                            continue;
                        }
                        // use comma as separator
                        String[] country = line.split(txtSplitBy);
                        if (!country[1].equals("0") && !country[2].equals("0")) {
                            String str = "insert into car_data ( car_id , longitude , latitude ,speed ,direction , time) values (" + country[0] + "," + country[1] + "," + country[2] + "," + country[3] + "," + country[4] + ",\"" + country[5] + "\");";
                            //System.out.println(str);
                            bw.write(str);
                            bw.newLine();
                            lineCount++;
                            totalCount++;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(file + "文件录入了" + lineCount + " 行数据！");
                lineCount = 0;
                bw.flush();
                bw.close();
            }
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }

        System.out.println("总共录入了" + titleCount + " 个文件！");
        System.out.println("共计录入了" + totalCount + " 行数据！");
    }
}
