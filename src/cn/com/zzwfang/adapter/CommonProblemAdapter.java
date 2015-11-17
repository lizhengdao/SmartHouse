package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CommonProblemBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonProblemAdapter extends BaseAdapter {

    
    private Context context;
    
    private ArrayList<CommonProblemBean> commonProblems;
    
    public CommonProblemAdapter(Context context, ArrayList<CommonProblemBean> commonProblems) {
        this.context = context;
        this.commonProblems = commonProblems;
    }
    
    @Override
    public int getCount() {
        if (commonProblems == null) {
            return 0;
        }
        return commonProblems.size();
    }

    @Override
    public Object getItem(int position) {
        return commonProblems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_common_problem, null);
        }
        
        TextView tvQuestion = (TextView) convertView.findViewById(R.id.adapter_common_problem_question);
        TextView tvAnswer = (TextView) convertView.findViewById(R.id.adapter_common_problem_answer);
        
        CommonProblemBean commonProblemBean = commonProblems.get(position);
        tvQuestion.setText(commonProblemBean.getQuestion());
        tvAnswer.setText(commonProblemBean.getAnswer());
        return convertView;
    }

}
