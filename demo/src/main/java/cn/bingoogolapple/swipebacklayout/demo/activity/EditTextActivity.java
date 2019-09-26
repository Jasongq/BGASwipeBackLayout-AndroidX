package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.baseadapter.BGARVVerticalScrollHelper;
import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.adapter.ContentAdapter;


/**
 * Created by yyl on 2017/1/18.
 */
public class EditTextActivity extends BaseActivity {
    private RecyclerView mContentRv;
    private ContentAdapter mContentAdapter;
    private EditText mMsgEt;
    private BGARVVerticalScrollHelper mRecyclerViewScrollHelper;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_text);
        mContentRv = getViewById(R.id.recyclerView);
        mMsgEt = getViewById(R.id.et_edit_text_msg);
    }

    @Override
    protected void setListener() {
        mContentAdapter = new ContentAdapter(mContentRv);
        mContentAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(parent.getContext(), "点击了条目 " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mContentAdapter.setOnRVItemLongClickListener(new BGAOnRVItemLongClickListener() {
            @Override
            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(parent.getContext(), "长按了条目 " + (position + 1), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mContentRv);
        mMsgEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String msg = mMsgEt.getText().toString().trim();
                    if (!TextUtils.isEmpty(msg)) {
                        mMsgEt.setText("");
                        mContentAdapter.addLastItem(msg);
                        mRecyclerViewScrollHelper.scrollToPosition(mContentAdapter.getItemCount() - 1);
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initToolbar();

        mContentRv.addItemDecoration(BGADivider.newBitmapDivider());
        mContentRv.setAdapter(mContentAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("测试 EditText 位于底部的情况");
        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

}
