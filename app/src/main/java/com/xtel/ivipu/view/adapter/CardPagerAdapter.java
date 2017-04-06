package com.xtel.ivipu.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.utils.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * Created by vivhp on 4/4/2017.
 */

public class CardPagerAdapter extends PagerAdapter {

    String user_name = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_FULL_NAME);
    LayoutInflater layoutInflater;
    ArrayList<MemberObj> arrayList;
    private Context context;

    public CardPagerAdapter(Context context, ArrayList<MemberObj> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.pager_item, container, false);

        MemberObj memberObj = arrayList.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_card_type);
        TextView tv_card_user_name = (TextView) itemView.findViewById(R.id.tv_card_user_name);
        TextView tv_card_date_create = (TextView) itemView.findViewById(R.id.tv_date_created);

        WidgetHelper.getInstance().setAvatarImageURL(imageView, memberObj.getMember_card());
        WidgetHelper.getInstance().setTextViewNoResult(tv_card_user_name, user_name);
        WidgetHelper.getInstance().setTextViewDate(tv_card_date_create, "Ngày tạo: ", memberObj.getCreate_time());
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
