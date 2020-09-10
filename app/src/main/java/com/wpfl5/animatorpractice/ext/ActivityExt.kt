package com.wpfl5.animatorpractice.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.Serializable

inline fun <reified T> Context.getSystemService(): T? =
    ContextCompat.getSystemService(this, T::class.java)

val Context.inputMethodManager get() = getSystemService<InputMethodManager>()

fun Activity.hideKeyboard() {
    inputMethodManager?.hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
    window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    currentFocus?.clearFocus()
}

fun Activity.showKeyboard(et: EditText) {
    et.requestFocus()
    inputMethodManager?.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideKeyboard(view: View) {
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}
/**
    startKtxActivity<AnotherActivity>()
    startKtxActivity<AnotherActivity>(flag = Intent.FLAG_ACTIVITY_NEW_TASK)
    startKtxActivityForResult<AnotherActivity>(requestCode = 1024)
    startKtxActivity<AnotherActivity>(
                "int" to 1,
                "boolean" to true,
                "string" to "multi value"
    )

    startKtxActivity<AnotherActivity>(
                extra = Bundle().apply {
                    putInt("int", 2)
                    putBoolean("boolean", true)
                    putString("string", "from bundle")
                }
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((resultCode == Activity.RESULT_OK) and (requestCode == 1024)) {
            toast("onActivityResult")
        }

    }
 */
inline fun <reified T : Activity> Activity.startAct(
    vararg values: Pair<String, Any>,
    flag: Int? = null,
    extra: Bundle? = null
) =
    startActivity(getIntent<T>(flag, extra, *values))


inline fun <reified T : Activity> Fragment.startAct(
    flag: Int? = null,
    extra: Bundle? = null,
    vararg values: Pair<String, Any>
) =
    activity?.let {
        startActivity(it.getIntent<T>(flag, extra, *values))
    }


inline fun <reified T : Activity> Activity.startActForResult(
    requestCode: Int,
    flag: Int? = null,
    extra: Bundle? = null,
    vararg values: Pair<String, Any>
) =
    startActivityForResult(getIntent<T>(flag, extra, *values), requestCode)


inline fun <reified T : Activity> Fragment.startActForResult(
    requestCode: Int,
    flag: Int? = null,
    extra: Bundle? = null,
    vararg values: Pair<String, Any>
) =
    activity?.let {
        startActivityForResult(activity?.getIntent<T>(flag, extra, *values), requestCode)
    }

inline fun <reified T : Context> Context.getIntent(
    flag: Int? = null,
    extra: Bundle? = null,
    vararg pairs: Pair<String, Any>
): Intent =
    Intent(this, T::class.java).apply {
        flag?.let { setFlags(flags) }
        extra?.let { putExtras(extra) }
        pairs.forEach { pair ->
            val name = pair.first
            when (val value = pair.second) {
                is Int -> putExtra(name, value)
                is Byte -> putExtra(name, value)
                is Char -> putExtra(name, value)
                is Short -> putExtra(name, value)
                is Boolean -> putExtra(name, value)
                is Long -> putExtra(name, value)
                is Float -> putExtra(name, value)
                is Double -> putExtra(name, value)
                is String -> putExtra(name, value)
                is CharSequence -> putExtra(name, value)
                is Parcelable -> putExtra(name, value)
                is Array<*> -> putExtra(name, value)
                is ArrayList<*> -> putExtra(name, value)
                is Serializable -> putExtra(name, value)
                is BooleanArray -> putExtra(name, value)
                is ByteArray -> putExtra(name, value)
                is ShortArray -> putExtra(name, value)
                is CharArray -> putExtra(name, value)
                is IntArray -> putExtra(name, value)
                is LongArray -> putExtra(name, value)
                is FloatArray -> putExtra(name, value)
                is DoubleArray -> putExtra(name, value)
                is Bundle -> putExtra(name, value)
                is Intent -> putExtra(name, value)
                else -> {
                }
            }
        }
    }