package com.huawei.signature.diff;

import static com.huawei.signature.diff.DBOpenHelper.DATABASE_APP_LIST_TABLE_NAME;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;


public class SignatureService extends Service {
    private static final String TAG = SignatureService.class.getSimpleName();
    private SQLiteDatabase mDatabase;
    private DBOpenHelper mSqliteHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mSqliteHelper = new DBOpenHelper(this);
        this.mDatabase = mSqliteHelper.getWritableDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        this.mSqliteHelper.close();
        super.onDestroy();
    }

    private final ISignatureService.Stub binder = new ISignatureService.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.d(TAG, "onTransact: " + Binder.getCallingUid() + ", " + Binder.getCallingPid());
            Log.d(TAG, "onTransact: " + getPackageManager().getNameForUid(Binder.getCallingUid()));

            if (Binder.getCallingPid() > 10000) {
                Log.w(TAG, "Illegal");
                reply.writeException(new UnsupportedOperationException("Illegal"));
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        /**
         * @param packageName
         * @param isDiffSignatureRequired
         * @return
         * @throws RemoteException
         */
        @Override
        public String[] querySignature(String packageName, boolean isDiffSignatureRequired) throws RemoteException {
            Log.d(TAG, "packageName: " + packageName + ", isDiffSignatureRequired:" + isDiffSignatureRequired);
            try (Cursor cursor = mDatabase.query(DATABASE_APP_LIST_TABLE_NAME, null, "name=?",
                    new String[]{packageName}, null, null, null)) {
                switch (cursor.getCount()) {
                    case 0:
                        return getResult(isDiffSignatureRequired);
                    case 1:
                        if (cursor.moveToFirst()) {
                            int isDiff = cursor.getInt(1);
                            return getResult(isDiff == 1);
                        }
                        Log.w(SignatureService.TAG, "Illegal 1");
                        break;
                    default:
                        throw new IllegalArgumentException("result size: " + cursor.getCount());
                }

            } catch (Exception e) {
                Log.w(TAG, e);
            }
            return getResult(false);
        }

        private String[] getResult(boolean isDiffSignatureRequired) {
            Log.d(TAG, "isDiffSignatureRequired:" + isDiffSignatureRequired);
            if (isDiffSignatureRequired) {
                return getResources().getStringArray(R.array.diff_signature);
            } else {
                return new String[] {getString(R.string.real_signature),};
            }
        }
    };
}