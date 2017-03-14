package cn.com.zzwfang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.adapter.RentHouseAdapter;
import cn.com.zzwfang.bean.FieldNameValueBean;
import cn.com.zzwfang.bean.HouseSourceParamBean;
import cn.com.zzwfang.bean.NameValueBean;
import cn.com.zzwfang.bean.RentHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseSourceParamPickListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseSourceSortTypeClickListener;

import com.alibaba.fastjson.JSON;

/**
 * 租房列表页
 * 获取租房列表，输入
 * 城市ID、
 * 区域ID、
 * 租金范围、
 * 面积范围、
 * 排序（0：默认 1：租金低到高 2：租金高到低）、
 * 分页大小、页码。
 * 
 * 租金区间
 * 面积范围
 * 排序
 * 朝向
 * 房型
 * 
 * @author lzd
 *
 */
public class RentHouseActivity extends BaseActivity implements OnClickListener,
        OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener,
        OnHouseSourceSortTypeClickListener, OnHouseSourceParamPickListener {

	public static final String INTENT_KEY_WORDS = "RentHouseActivity.intent_keywords";
    private TextView tvBack;

    private EditText edtKeyWords;
    private LinearLayout lltArea, lltRentPrice, lltHouseType, lltMore;
    private TextView tvArea, tvRentPrice, tvHouseType, tvMore;
    private ImageView imgClearKeyWords;

    private AbPullToRefreshView pullView;
    private ListView lstRentHouseView;
    private AutoDrawableTextView tvSort;
    private RentHouseAdapter adapter;
    
    private LinearLayout lltRentHouseSourceParam;
    private View lineAnchor, lineOne, lineTwo, lineThree;


    private String cityId = "";

    private ArrayList<RentHouseBean> rentHouses = new ArrayList<RentHouseBean>();

    private String keyWords;
    private int pageIndex = 1;
    private int pageTotal = 0;
    
    private ArrayList<HouseSourceParamBean> houseSourceParams;
    
//  排序参数
	private ArrayList<FieldNameValueBean> sortParamList;
	/**
	 * 排序（已选择的）
	 */
	private FieldNameValueBean sortTypeBean;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_rent_house);
        cityId = getIntent().getStringExtra(
                HomeRecommendHouseAdapter.INTENT_CITY_ID);
        keyWords = getIntent().getStringExtra(INTENT_KEY_WORDS);
        initView();
        initData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.act_rent_house_back);
        edtKeyWords = (EditText) findViewById(R.id.act_rent_house_search_edt);
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_rent_house);
        lstRentHouseView = (ListView) findViewById(R.id.lst_rent_house);
        imgClearKeyWords = (ImageView) findViewById(R.id.act_rent_house_clear_key_wrods);

        lltArea = (LinearLayout) findViewById(R.id.act_rent_house_area_llt);
        lltRentPrice = (LinearLayout) findViewById(R.id.act_rent_house_rent_price_llt);
        lltHouseType = (LinearLayout) findViewById(R.id.act_rent_house_type_llt);
        lltMore = (LinearLayout) findViewById(R.id.act_rent_house_more_llt);
        
        lineAnchor = findViewById(R.id.line_rent_house_anchor);
        lineOne = findViewById(R.id.line_rent_house_one);
        lineTwo = findViewById(R.id.line_rent_house_two);
        lineThree = findViewById(R.id.line_rent_house_three);
        
        lltRentHouseSourceParam = (LinearLayout) findViewById(R.id.llt_rent_house_params);

        tvArea = (TextView) findViewById(R.id.act_rent_house_area_tv);
        tvRentPrice = (TextView) findViewById(R.id.act_rent_house_rent_price_tv);
        tvHouseType = (TextView) findViewById(R.id.act_rent_house_type_tv);
        tvSort = (AutoDrawableTextView) findViewById(R.id.tv_rent_house_sort);
        tvMore = (TextView) findViewById(R.id.act_rent_house_more_tv);
        
        if (!TextUtils.isEmpty(keyWords)) {
            edtKeyWords.setText(keyWords);
        }

        tvBack.setOnClickListener(this);
        pullView.setPullRefreshEnable(true);
        pullView.setLoadMoreEnable(true);
        pullView.setOnHeaderRefreshListener(this);
        pullView.setOnFooterLoadListener(this);

        lltArea.setOnClickListener(this);
        lltRentPrice.setOnClickListener(this);
        lltHouseType.setOnClickListener(this);
        lltMore.setOnClickListener(this);
        imgClearKeyWords.setOnClickListener(this);

        adapter = new RentHouseAdapter(this, rentHouses);
        lstRentHouseView.setAdapter(adapter);
        lstRentHouseView.setOnItemClickListener(this);
        
        tvSort.setOnClickListener(this);
        
        edtKeyWords.setOnEditorActionListener(new OnEditorActionListener() {
            
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId  == EditorInfo.IME_ACTION_SEARCH) {
                    keyWords = edtKeyWords.getText().toString();
                    getRentHouseList(cityId, keyWords, 10, true);
                    return true;
                }
                return false;
            }
        });



    }

    private void initData() {
        
    	getHouseSourceParam();
    	getHouseSourceSort();
        getRentHouseList(cityId, keyWords, 10, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_rent_house_back: // 返回
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView()
                    .getWindowToken(), 0);
            finish();
            break;
        case R.id.act_rent_house_area_llt: // 区域
//            PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
//                    onAreaSelectListener);
        	if (houseSourceParams != null) {
				PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 0, houseSourceParams, this);
			}
            break;
        case R.id.act_rent_house_rent_price_llt: // 租金
//            PopViewHelper.showSelectRentPricePopWindow(this, lltRentPrice,
//                    rentPriceRanges, onRentPriceSelectListener);
        	if (houseSourceParams != null) {
            	PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 1, houseSourceParams, this);
			}
            break;
        case R.id.act_rent_house_type_llt: // 房型
//            PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
//                    houseTypes, onHouseTypeSelectListener);
        	if (houseSourceParams != null) {
            	PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 2, houseSourceParams, this);
			}
            break;
        case R.id.act_rent_house_more_llt: // 更多
        	// TODO
//            PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
//                    sorts, directions, estateLabels, lltMore);
//        	PopViewHelper.showRentHouseMorePopWindow(this, moreType, sorts,
//        			squareRanges, directions, lltMore, onRentHouseMoreConditionListener);
        	if (houseSourceParams != null) {
				if (houseSourceParams.size() > 4) {
					PopViewHelper.showPickHouseSourceParamMorePopWindow(this, lineAnchor, 4, houseSourceParams, this);
				} else {
					PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 3, houseSourceParams, this);
				}
				
			}
            break;
        case R.id.act_rent_house_clear_key_wrods:
        	keyWords = "";
        	edtKeyWords.setText("");
        	break;
        case R.id.tv_rent_house_sort:  //  排序
        	if (sortParamList != null) {
				PopViewHelper.showHouseSourceSortTypeDialog(this, sortParamList, this);
			}
        	break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        RentHouseBean rentHouseBean = rentHouses.get(position);
        Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(RentHouseDetailActivity.INTENT_HOUSE_SOURCE_ID,
                        rentHouseBean.getId())
                .jump(this, RentHouseDetailActivity.class);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        if (pageIndex > pageTotal) {
            getRentHouseList(cityId, keyWords, 10, false);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
    	keyWords = edtKeyWords.getText().toString();
        getRentHouseList(cityId, keyWords, 10, true);
    }



    private void getRentHouseList(String cityId, String searchKeyWords, int pageSize,
            final boolean isRefresh) {
    	

        if (isRefresh) {
            pageIndex = 1;
        }
        
        HashMap<String, String> requestParams = new HashMap<String, String>();
        if (houseSourceParams != null) {
			for (HouseSourceParamBean para : houseSourceParams) {
				ArrayList<NameValueBean> nameValues = para.getValues();
				for (NameValueBean nameValueBean : nameValues) {
					if (nameValueBean.isSelected()) {
						requestParams.put(para.getFiled(), nameValueBean.getValue());
						break;
					}
				}
			}
		}
        
        if (sortTypeBean != null) {
        	requestParams.put(sortTypeBean.getFiled(), sortTypeBean.getValue());
        }

        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getRentHouseList(cityId, requestParams, keyWords,
                10, pageIndex, new ResultHandlerCallback() {

                    @Override
                    public void rc999(RequestEntity entity, Result result) {
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }

                    @Override
                    public void rc3001(RequestEntity entity, Result result) {
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }

                    @Override
                    public void rc0(RequestEntity entity, Result result) {
                        int total = result.getTotal();
                        pageTotal = (int) Math.ceil(((double) total / (double) 10));
                        
                        ArrayList<RentHouseBean> temp = (ArrayList<RentHouseBean>) JSON
                                .parseArray(result.getData(),
                                        RentHouseBean.class);
                        
                        if (isRefresh) {
                            rentHouses.clear();
                        }
                        if (temp != null) {
                            pageIndex++;
                        }
                        rentHouses.addAll(temp);
                        adapter.notifyDataSetChanged();
                        
                        if (isRefresh) {
                            pullView.onHeaderRefreshFinish();
                        } else {
                            pullView.onFooterLoadFinish();
                        }
                    }
                });
    }
    
    
    private void getHouseSourceParam() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceParameter(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO getHouseSourceParam
//				Log.i("--->", "getHouseSourceParam result: " + result.getData());
				houseSourceParams = (ArrayList<HouseSourceParamBean>) JSON.parseArray(result.getData(), HouseSourceParamBean.class);
				if (houseSourceParams != null) {
					
					Iterator<HouseSourceParamBean> iterator = houseSourceParams.iterator();
					while (iterator.hasNext()) {
						HouseSourceParamBean houseSourceParamBean = iterator.next();
						if ("售价".equals(houseSourceParamBean.getName())) {
							iterator.remove();
						} else {
                            ArrayList<NameValueBean> values = houseSourceParamBean.getValues();
							if (values != null && values.size() > 0) {
								NameValueBean nameValueBean = values.get(0);
								if ("不限".equals(nameValueBean.getName())) {
									nameValueBean.setSelected(true);
								}
							}
						}
					}
					
					int size = houseSourceParams.size();
					if (size == 0) {
						lltRentHouseSourceParam.setVisibility(View.GONE);
					} else if (size == 1) {
						tvArea.setText(houseSourceParams.get(0).getName());
						lltArea.setVisibility(View.VISIBLE);
						
						lltRentPrice.setVisibility(View.GONE);
						lltHouseType.setVisibility(View.GONE);
						lltMore.setVisibility(View.GONE);
						
						lineOne.setVisibility(View.GONE);
						lineTwo.setVisibility(View.GONE);
						lineThree.setVisibility(View.GONE);
					} else if (size == 2) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvRentPrice.setText(houseSourceParams.get(1).getName());
						
						lltArea.setVisibility(View.VISIBLE);
						lltRentPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.GONE);
						lltMore.setVisibility(View.GONE);
						
						lineTwo.setVisibility(View.GONE);
						lineThree.setVisibility(View.GONE);
					} else if (size == 3) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvRentPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
						
						lltArea.setVisibility(View.VISIBLE);
						lltRentPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.GONE);
						
						lineThree.setVisibility(View.GONE);
						
					} else if (size == 4) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvRentPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
						tvMore.setText(houseSourceParams.get(3).getName());
						
						lltArea.setVisibility(View.VISIBLE);
						lltRentPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.VISIBLE);
					} else if (size > 4) {
						lltArea.setVisibility(View.VISIBLE);
						lltRentPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.VISIBLE);
						
						tvArea.setText(houseSourceParams.get(0).getName());
						tvRentPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
					}
				}
			}
		});
	}
    
    private void getHouseSourceSort() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceSort(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO getHouseSourceSort
//				Log.i("--->", "getHouseSourceSort result: " + result.getData());
				sortParamList = (ArrayList<FieldNameValueBean>) JSON.parseArray(result.getData(), FieldNameValueBean.class);
				if (sortParamList != null && sortParamList.size() > 0) {
					
					
					sortTypeBean = sortParamList.get(0);
					sortTypeBean.setSelected(true);
				}
			}
		});
	}
    
	
	@Override
	protected int getStatusBarTintResource() {
		// TODO Auto-generated method stub
		return R.color.white;
	}

	@Override
	public void onHouseSourceSortTypeClick(FieldNameValueBean sortType) {
		// TODO 排序
		sortTypeBean = sortType;
		keyWords = edtKeyWords.getText().toString();
		getRentHouseList(cityId, keyWords, 10, true);
	}

	@Override
	public void onHouseSourceParamPick(int fieldPosition,
			NameValueBean houseSourceParam) {
		// TODO 房源参数选择
		keyWords = edtKeyWords.getText().toString();
		getRentHouseList(cityId, keyWords, 10, true);
	}


}
