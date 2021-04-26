package tranvu203107.dmt.nms;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private float[] yData = {15f,30f,55f};  //giá trị % của các Status
    private String[] xData = {"Pending","Processing","Done"};   //label tương ứng cho các Status
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        pieChart = (PieChart)findViewById(R.id.PieChart);

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);  //cho phép xoay
        pieChart.setHoleRadius(0f);         //tên của chart, được viết trong 1 vòng tròn ở giữa chart với bán kính này
        pieChart.setTransparentCircleAlpha(0);  // vòng tròng trong suốt, chắc để tạo thêm hiệu ứng cho đẹp?
        //pieChart.setDrawEntryLabels(true);
        
        addDataSet();
    }

    /**
     * hàm này để gán các gí trị vào Piechart
     * trong đó, yEntry là các phần tử sẽ có trong chart, gồm chỉ số % và tên của chỉ số đó
     * PieDataset là bộ dữ liệu truyền vào cho chart, gồm các giá trị, màu sắc, khoảng trắng giữa các phần, cỡ chữ, màu chữ
     * PieData nhận các giá trị từ PieDataset rồi gán cho PieChart
     * invalidate() chắc là dùng để chốt các thay đổi
     */
    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for(int i=0;i<yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}