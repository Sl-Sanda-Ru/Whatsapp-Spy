package lk.sanda.motive;

import android.app.Notification;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private final ArrayList<HashMap<String, Object>> respp = new ArrayList<>();
    private Toolbar _toolbar;
    private AppBarLayout _app_bar;
    private CoordinatorLayout _coordinator;
    private DrawerLayout _drawer;
    private HashMap<String, Object> resp = new HashMap<>();
    private double count = 0;
    private LinearLayout linear2;
    private TextView textview2;
    private TextView textview3;
    private MaterialButton materialbutton2;

    private RequestNetwork http;
    private RequestNetwork.RequestListener _http_request_listener;
    private Notification noti;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.home);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _app_bar = findViewById(R.id._app_bar);
        _coordinator = findViewById(R.id._coordinator);
        _toolbar = findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        _drawer = findViewById(R.id._drawer);
        ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(HomeActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();

        LinearLayout _nav_view = findViewById(R.id._nav_view);

        linear2 = findViewById(R.id.linear2);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        materialbutton2 = findViewById(R.id.materialbutton2);
        http = new RequestNetwork(this);

        materialbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                count++;
                textview2.setText(resp.get("quote").toString());
                textview3.setText("- ".concat(resp.get("author").toString()));
                http.startRequestNetwork(RequestNetworkController.GET, "https://qapi.vercel.app/api/random", "A", _http_request_listener);
            }
        });

        _http_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                resp = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>() {
                }.getType());
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

            }
        };
    }

    private void initializeLogic() {
        http.startRequestNetwork(RequestNetworkController.GET, "https://qapi.vercel.app/api/random", "A", _http_request_listener);
    }

    @Override
    public void onBackPressed() {
        if (_drawer.isDrawerOpen(GravityCompat.START)) {
            _drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
