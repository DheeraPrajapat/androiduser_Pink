package com.marius.valeyou.ui.activity.languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityAppLanguageBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;

public class AppLanguageActivity extends AppActivity<ActivityAppLanguageBinding,AppLanguageActivityVM> {

    String langCode="en";

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, AppLanguageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<AppLanguageActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_app_language, AppLanguageActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(AppLanguageActivityVM vm) {
        String lang = sharedPref.get("language","en");

        if (lang.equalsIgnoreCase("en")){
            binding.tickImgEng.setVisibility(View.VISIBLE);
            binding.tickImgPort.setVisibility(View.GONE);
        }else if (lang.equalsIgnoreCase("pt")){
            binding.tickImgEng.setVisibility(View.GONE);
            binding.tickImgPort.setVisibility(View.VISIBLE);
        }

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){

                    case R.id.save:

                        setLocale(AppLanguageActivity.this,langCode);
                        sharedPref.put("language",langCode);

                        if (viewModel.sharedPref.getUserData()!=null){
                            Intent intent = MainActivity.newIntent(AppLanguageActivity.this,"lang");
                            startNewActivity(intent,true,true);
                            finish(true);
                        }else{
                            Intent intent = LoginActivity.newIntent(AppLanguageActivity.this);
                            startNewActivity(intent,true,true);
                            finish(true);
                        }


                        break;

                    case R.id.ll_portLang:
                        langCode = "pt";
                        binding.tickImgPort.setVisibility(View.VISIBLE);
                        binding.tickImgEng.setVisibility(View.GONE);

                        break;

                    case R.id.ll_engLang:
                        langCode = "en";
                        binding.tickImgEng.setVisibility(View.VISIBLE);
                        binding.tickImgPort.setVisibility(View.GONE);
                        break;


                }
            }
        });
    }
}