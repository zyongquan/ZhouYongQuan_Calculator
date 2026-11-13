package com.example.zhouyongquan_calculator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
 * 第一个 Activity：输入两个数与运算符，调用 [Calculator] 计算并显示结果。
 *
 * 点击标题栏的“历史”菜单可以打开 [HistoryActivity] 查看计算历史。
 */
class MainActivity : AppCompatActivity() {

    private val calculator = Calculator()

    private lateinit var firstInput: TextInputEditText
    private lateinit var secondInput: TextInputEditText
    private lateinit var operatorGroup: RadioGroup
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        HistoryStore.init(this)
        bindViews()
        applyWindowInsets()
        setUpToolbar()

        // 横竖屏切换后恢复上一次的计算结果。
        resultText.text = savedInstanceState?.getString(KEY_RESULT)
            ?: getString(R.string.result_placeholder)

        findViewById<MaterialButton>(R.id.calculateButton).setOnClickListener {
            calculate()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RESULT, resultText.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_history -> {
            startActivity(Intent(this, HistoryActivity::class.java))
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun bindViews() {
        firstInput = findViewById(R.id.firstInput)
        secondInput = findViewById(R.id.secondInput)
        operatorGroup = findViewById(R.id.operatorGroup)
        resultText = findViewById(R.id.resultText)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        toolbar.title = getString(R.string.main_title)
    }

    /** 读取输入、执行计算并显示结果。 */
    private fun calculate() {
        val first = firstInput.text?.toString()?.trim()?.toDoubleOrNull()
        val second = secondInput.text?.toString()?.trim()?.toDoubleOrNull()
        if (first == null || second == null) {
            showMessage(getString(R.string.error_number))
            return
        }

        val operator = selectedOperator() ?: run {
            showMessage(getString(R.string.error_operator))
            return
        }

        try {
            val result = calculator.calculate(first, second, operator)
            resultText.text = NumberFormatter.format(result)
            HistoryStore.add(
                HistoryRecord(
                    first = first,
                    operator = operator,
                    second = second,
                    result = result,
                    time = System.currentTimeMillis()
                )
            )
        } catch (exception: IllegalArgumentException) {
            resultText.text = getString(R.string.result_placeholder)
            showMessage(exception.message ?: getString(R.string.error_divide_by_zero))
        }
        hideKeyboard()
    }

    private fun selectedOperator(): Char? = when (operatorGroup.checkedRadioButtonId) {
        R.id.opAdd -> '+'
        R.id.opSubtract -> '-'
        R.id.opMultiply -> '*'
        R.id.opDivide -> '/'
        else -> null
    }

    private fun hideKeyboard() {
        val manager = getSystemService(InputMethodManager::class.java)
        manager?.hideSoftInputFromWindow(resultText.windowToken, 0)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        const val KEY_RESULT = "key_result"
    }
}
