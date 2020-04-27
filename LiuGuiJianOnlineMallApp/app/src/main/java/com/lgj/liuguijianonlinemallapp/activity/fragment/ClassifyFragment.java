package com.lgj.liuguijianonlinemallapp.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.AddressActivity;
import com.lgj.liuguijianonlinemallapp.adapter.ClassifyGoodsRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.adapter.ClassifyListViewAdapter;
import com.lgj.liuguijianonlinemallapp.adapter.ClassifyRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Classify;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassifyFragment extends Fragment {
    private ExpandableListView elv_first_second;
    private RecyclerView rv_third, rv_content;
    private ClassifyListViewAdapter listViewAdapter;
    private List<Classify> classifies = new ArrayList<>();
    private List<Classify> classifies2 = new ArrayList<>();
    private List<Goods> goods = new ArrayList<>();
    private ClassifyRecyclerViewAdapter recyclerViewAdapter;
    private ClassifyGoodsRecyclerViewAdapter goodsRecyclerViewAdapter;
    private int first = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_classify, container, false);
        init(view);
        loadClassify();
        bind();
        return view;
    }

    private void bind() {
        elv_first_second.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (first == i) {
                    boolean groupExpanded = elv_first_second.isGroupExpanded(i);
                    if (groupExpanded) {
                        elv_first_second.collapseGroup(i);
                    } else {
                        elv_first_second.expandGroup(i);
                    }
                } else {
                    first = i;
                    for (int x = 0; x < elv_first_second
                            .getExpandableListAdapter().getGroupCount(); x++) {
                        if (i != x) {// 关闭其他分组
                            elv_first_second.collapseGroup(x);
                        } else {
                            elv_first_second.expandGroup(x);
                        }
                    }
                    classifies2.clear();
                    classifies2 .addAll(classifies.get(i).getChildName().get(0).getChildName());
                    recyclerViewAdapter.notifyDataSetChanged();
                    loadGoods(classifies2.get(0).getName());
                }

                return true;
            }
        });
        elv_first_second.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                classifies2.clear();
                classifies2 .addAll(classifies.get(i).getChildName().get(i1).getChildName());
                recyclerViewAdapter.notifyDataSetChanged();
                loadGoods(classifies2.get(0).getName());
                return true;
            }
        });
        recyclerViewAdapter.setOnitemClickListener(new ClassifyRecyclerViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                loadGoods(classifies2.get(postion).getName());
            }
        });
    }

    private void loadClassify() {
        Map<String, String> map = new HashMap<>();
        RequestParams params = new RequestParams(map);
        RequestParamConfig.classify(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult<List<Classify>>>() {
                }.getType();
                ServerResult<List<Classify>> result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    classifies.clear();
                    classifies = result.getData();
                    listViewAdapter = new ClassifyListViewAdapter(getContext(), classifies);
                    elv_first_second.setAdapter(listViewAdapter);
                    elv_first_second.expandGroup(0);
                    classifies2.clear();
                    classifies2.addAll(classifies.get(0).getChildName().get(0).getChildName());
                    recyclerViewAdapter.notifyDataSetChanged();
                    loadGoods(classifies.get(0).getChildName().get(0).getName());
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGoods(String thirdClassify) {
        Map<String, String> map = new HashMap<>();
        map.put("thirdClassify", thirdClassify);
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getGoodsByTClassify(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult<List<Goods>>>() {
                }.getType();
                ServerResult<List<Goods>> result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    if (goods != null && goods.size() > 0) {
                        goods.clear();
                    }
                    goods .addAll( result.getData());
                    goodsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view) {
        elv_first_second = view.findViewById(R.id.elv_first_second);
        rv_third = view.findViewById(R.id.rv_third);
        recyclerViewAdapter = new ClassifyRecyclerViewAdapter(getContext(), classifies2);
        rv_third.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_third.setAdapter(recyclerViewAdapter);
        rv_content = view.findViewById(R.id.rv_content);
        goodsRecyclerViewAdapter = new ClassifyGoodsRecyclerViewAdapter(getContext(), goods);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(goodsRecyclerViewAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
