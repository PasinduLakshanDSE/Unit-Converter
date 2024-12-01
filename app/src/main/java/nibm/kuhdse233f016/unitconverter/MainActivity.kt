package nibm.kuhdse233f016.unitconverter

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var txtType: Spinner
    private lateinit var txtValue: EditText
    private lateinit var btnClick: ImageButton
    private lateinit var lblValue: TextView
    private lateinit var txtType2: Spinner
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        txtType = findViewById(R.id.TxtType)
        txtValue = findViewById(R.id.TxtValue)
        btnClick = findViewById(R.id.BtnConvert)
        lblValue = findViewById(R.id.LblValue)
        txtType2 = findViewById(R.id.TxtType2)
        radioGroup = findViewById(R.id.Rgroup)

        //  default spinner values
        configureSpinners("Distance")


        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.Rbtndistance -> configureSpinners("Distance")
                R.id.RbtnTemp -> configureSpinners("Temperature")
                R.id.RbtnWeight -> configureSpinners("Weight")
            }
        }


        btnClick.setOnClickListener { performConversion() }
    }


    private fun configureSpinners(type: String) {
        val spinner1Options: List<String>
        val spinner2Options: List<String>

         //Spinner value
        when (type) {
            "Distance" -> {
                spinner1Options = listOf("Kilometers", "Centimeters")
                spinner2Options = listOf("Centimeters", "Kilometers")
            }
            "Temperature" -> {
                spinner1Options = listOf("Fahrenheit", "Celsius")
                spinner2Options = listOf("Fahrenheit", "Celsius")
            }
            "Weight" -> {
                spinner1Options = listOf("Kilograms", "Grams")
                spinner2Options = listOf("Grams", "Kilograms")
            }
            else -> {
                spinner1Options = listOf()
                spinner2Options = listOf()
            }
        }


        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner1Options)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner2Options)

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        txtType.adapter = adapter1
        txtType2.adapter = adapter2
    }


    private fun performConversion() {
        val valueStr = txtValue.text.toString()
        if (valueStr.isEmpty()) {
            lblValue.text = "Please Enter a value!"
            return
        }

        val value = valueStr.toDouble()
        val fromUnit = txtType.selectedItem.toString()
        val toUnit = txtType2.selectedItem.toString()
        var result = 0.0

        when {
            // Distance conversions
            fromUnit == "Kilometers" && toUnit == "Centimeters" -> result = value * 100000
            fromUnit == "Centimeters" && toUnit == "Kilometers" -> result = value / 100000

            // Temperature conversions
            fromUnit == "Fahrenheit" && toUnit == "Celsius" -> result = (value - 32) * 5 / 9
            fromUnit == "Celsius" && toUnit == "Fahrenheit" -> result = (value * 9 / 5) + 32

            // Weight conversions
            fromUnit == "Kilograms" && toUnit == "Grams" -> result = value * 1000
            fromUnit == "Grams" && toUnit == "Kilograms" -> result = value / 1000

            else -> lblValue.text = "Invalid conversion!"
        }

        lblValue.text = "Converted Value: $result"
    }
}
