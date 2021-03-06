package com.afilini.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import org.magicalbitcoin.wallet.Lib
import org.magicalbitcoin.wallet.Types.Network
import org.magicalbitcoin.wallet.Types.WalletConstructor
import org.magicalbitcoin.wallet.Types.WalletPtr

class MainActivity : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("magical_bitcoin_wallet_jni")
        }
    }

    private val TAG = "MainActivity"
    private var wallet: WalletPtr? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        wallet = Lib().constructor(WalletConstructor("test", Network.regtest, filesDir.toString(), "wpkh(tprv8ZgxMBicQKsPexGYyaFwnAsCXCjmz2FaTm6LtesyyihjbQE3gRMfXqQBXKM43DvC1UgRVv1qom1qFxNMSqVAs88qx9PhgFnfGVUdiiDf6j4/0/*)", null, "tcp://tn.not.fyi:55001", null, null))
        fab.setOnClickListener { view ->
            val lib = Lib()

            Log.i(TAG, lib.get_new_address(wallet!!))

            lib.sync(wallet!!)

            Log.i(TAG, lib.get_balance(wallet!!).toString())
            Log.i(TAG, lib.list_unspent(wallet!!).toString())
            Log.i(TAG, lib.list_transactions(wallet!!).toString())

            // if wallet is reused after the destructor it should sigsev
            lib.destructor(wallet!!)
            wallet = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
