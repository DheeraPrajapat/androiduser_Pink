package com.marius.valeyou.ui.activity.categorytitle;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityCategoryTitleBinding;
import com.marius.valeyou.di.base.view.AppActivity;

import androidx.lifecycle.Observer;

public class CategoryTitleActivity extends AppActivity<ActivityCategoryTitleBinding, CategoryTitleActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, CategoryTitleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<CategoryTitleActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_category_title, CategoryTitleActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(CategoryTitleActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.add_job_des));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        binding.etTitle.setText(sharedPref.get("title",""));
        binding.etDescription.setText(sharedPref.get("description",""));

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_submit:
                        if (isEmpty()){
                            Intent intent = new Intent();
                            intent.putExtra("title",binding.etTitle.getText().toString());
                            intent.putExtra("description",binding.etDescription.getText().toString());

                            sharedPref.put("title",binding.etTitle.getText().toString());
                            sharedPref.put("description",binding.etDescription.getText().toString());

                            setResult(Activity.RESULT_OK,intent);
                            finish(true);
                        }
                        break;
                }
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(binding.etTitle.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etDescription.getText().toString().trim()))
            return false;

        return true;
    }
}
