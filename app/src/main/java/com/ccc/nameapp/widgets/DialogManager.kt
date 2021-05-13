package com.ccc.nameapp.widgets

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.annotation.ArrayRes
import com.ccc.nameapp.R
import com.ccc.nameapp.extension.convertToHtml
import com.ccc.nameapp.utils.DateUtils
import kotlinx.android.synthetic.main.layout_progress_bar.view.*
import java.util.Calendar

interface DialogManager {
    fun showProgressDialog(message: String)

    fun dismissProgressDialog()

    fun dismissDatePicker()

    fun datePickerDialog(onDateSetListener: DatePickerDialog.OnDateSetListener?): DialogManager

    fun datePickerDialogWithMinDate(
        dateString: String,
        dateFormat: String,
        onDateSetListener: DatePickerDialog.OnDateSetListener?
    ): DialogManager

    fun birthdayDatePickerDialog(
        dateString: String,
        dateFormat: String,
        onDateSetListener: DatePickerDialog.OnDateSetListener?
    ): DialogManager

    fun showDatePickerDialog()

    fun resetDatePickerDialog()

    fun updateDatePickDialog(year: Int, month: Int, day: Int)

    fun dialogBasic(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener
    )

    fun dialogBasic(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener,
        cancelDialogListener: DialogInterface.OnCancelListener
    )

    fun dialogBasicWithOkButton(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener
    )

    fun dialogBasicWithCloseButton(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener
    )

    fun dialogListSingleChoice(
        title: String,
        @ArrayRes arrayId: Int,
        selectedIndex: Int,
        itemSelectedListener: DialogInterface.OnClickListener
    )

    fun dialogBasic(
        title: String = "",
        message: String = "",
        titlePositiveButton: String,
        titleNegativeButton: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener
    )

    fun dialogBasic(
        title: String = "",
        message: String = "",
        titlePositiveButton: String,
        titleNegativeButton: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener,
        cancelDialogListener: DialogInterface.OnCancelListener
    )
}

class DialogManagerImpl(private var context: Context) : DialogManager {

    private var mProgressDialog: AlertDialog? = null
    private var mDatePickerDialog: DatePickerDialog? = null

    override fun showProgressDialog(message: String) {
        if (mProgressDialog == null) {
            val view = View.inflate(context, R.layout.layout_progress_bar, null)
            view.messageText.text = message
            mProgressDialog = AlertDialog.Builder(context).setView(view).create()
        }
        mProgressDialog?.run {
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    override fun dismissProgressDialog() {
        mProgressDialog?.run {
            dismiss()
            mProgressDialog = null
        }
    }

    override fun dismissDatePicker() {
        mDatePickerDialog?.run {
            dismiss()
            mDatePickerDialog = null
        }
    }

    override fun datePickerDialog(
        onDateSetListener: DatePickerDialog.OnDateSetListener?
    ): DialogManager {
        val calendar = Calendar.getInstance()
        mDatePickerDialog = DatePickerDialog(
            context, onDateSetListener, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )
        mDatePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - TIME_UNIT
        return this
    }

    override fun datePickerDialogWithMinDate(
        dateString: String,
        dateFormat: String,
        onDateSetListener: DatePickerDialog.OnDateSetListener?
    ): DialogManager {
        val calendar = Calendar.getInstance()
        if (dateString.isNotBlank()) {
            val time = DateUtils.convertStringToDate(dateString, dateFormat)
            calendar.time = time ?: Calendar.getInstance().time
        }
        mDatePickerDialog = DatePickerDialog(
            context, onDateSetListener, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )
        val timeStamp = DateUtils.convertDateStringToTimestamp(
            dateString,
            context.getString(R.string.dd_mm_yyyy)
        )
        if (timeStamp == null) {
            mDatePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - TIME_UNIT
        } else {
            mDatePickerDialog?.datePicker?.minDate = timeStamp
        }
        return this
    }

    override fun birthdayDatePickerDialog(
        dateString: String,
        dateFormat: String,
        onDateSetListener: DatePickerDialog.OnDateSetListener?
    ): DialogManager {
        val calendar = Calendar.getInstance()
        if (dateString.isNotBlank()) {
            val time = DateUtils.convertStringToDate(dateString, dateFormat)
            calendar.time = time ?: Calendar.getInstance().time
        }
        mDatePickerDialog = DatePickerDialog(
            context, onDateSetListener, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )
        mDatePickerDialog?.datePicker?.maxDate = System.currentTimeMillis() - TIME_UNIT
        return this
    }

    override fun showDatePickerDialog() {
        mDatePickerDialog?.show()
    }

    override fun resetDatePickerDialog() {
        val calendar = Calendar.getInstance()
        mDatePickerDialog?.updateDate(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun updateDatePickDialog(year: Int, month: Int, day: Int) {
        mDatePickerDialog?.updateDate(year, month, day)
    }

    override fun dialogBasic(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message.convertToHtml())
        builder.setPositiveButton(context.getString(R.string.ok), positiveButtonListener)
        builder.setNegativeButton(context.getString(R.string.cancel), negativeButtonListener)
        builder.show()
    }

    override fun dialogBasic(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener,
        cancelDialogListener: DialogInterface.OnCancelListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message.convertToHtml())
        builder.setPositiveButton(context.getString(R.string.ok), positiveButtonListener)
        builder.setNegativeButton(context.getString(R.string.cancel), negativeButtonListener)
        builder.setOnCancelListener(cancelDialogListener)
        builder.show()
    }

    override fun dialogBasic(
        title: String,
        message: String,
        titlePositiveButton: String,
        titleNegativeButton: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message.convertToHtml())
        builder.setPositiveButton(titlePositiveButton, positiveButtonListener)
        builder.setNegativeButton(titleNegativeButton, negativeButtonListener)
        builder.show()
    }

    override fun dialogBasic(
        title: String,
        message: String,
        titlePositiveButton: String,
        titleNegativeButton: String,
        positiveButtonListener: DialogInterface.OnClickListener,
        negativeButtonListener: DialogInterface.OnClickListener,
        cancelDialogListener: DialogInterface.OnCancelListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        builder.setMessage(message.convertToHtml())
        builder.setPositiveButton(titlePositiveButton, positiveButtonListener)
        builder.setNegativeButton(titleNegativeButton, negativeButtonListener)
        builder.setOnCancelListener(cancelDialogListener)
        builder.show()
    }

    override fun dialogBasicWithOkButton(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message.convertToHtml())
        builder.setCancelable(false)
        builder.setPositiveButton(context.getString(R.string.ok), positiveButtonListener)
        builder.show()
    }

    override fun dialogBasicWithCloseButton(
        title: String,
        message: String,
        positiveButtonListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message.convertToHtml())
        builder.setCancelable(false)
        builder.setPositiveButton(context.getString(R.string.close), positiveButtonListener)
        builder.show()
    }

    override fun dialogListSingleChoice(
        title: String,
        arrayId: Int,
        selectedIndex: Int,
        itemSelectedListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setSingleChoiceItems(
            context.resources.getTextArray(arrayId), selectedIndex,
            itemSelectedListener
        )
        builder.show()
    }

    companion object {
        private const val TIME_UNIT = 1000
    }
}
