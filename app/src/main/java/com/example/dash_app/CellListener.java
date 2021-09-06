package com.example.dash_app;

import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.util.Log;

import java.util.List;

public class CellListener extends PhoneStateListener {
    public CellListener() {
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        super.onCellInfoChanged(cellInfo);
        Log.d("cell listener", "info changed");
    }
}
