package `in`.tutorial.kotlinpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import `in`.tutorial.kotlinpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var conversions:Array<String>
    var conversionType:Int = 0
    val converionListHandler = object:AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position+1){
                1-> conversionType = 1
                2-> conversionType = 2
                3-> conversionType = 3
            }
            Toast.makeText(this@MainActivity,"Converion type $conversionType", Toast.LENGTH_LONG).show()
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            conversionType = 0
        }
    }
    var textHandler = object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                try {
                    val result:Float = convertVal(s.toString().toFloat())
                    binding.toValue.setText(result.toString())
                }catch (e:Exception){
                    Toast.makeText(this@MainActivity,"Invalid value entered", Toast.LENGTH_LONG).show()
                }
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conversions = resources.getStringArray(R.array.conversion)
        initSpinner()
        binding.conversionList.onItemSelectedListener = converionListHandler
        binding.fromValue.addTextChangedListener(textHandler)
    }
    fun convertVal(input:Float):Float{
        if(conversionType == 1){
          return ((input * (1.8))+32.0f).toFloat()
        }else if(conversionType == 2){
            return (input - 273.15f)
        }else if(conversionType == 3){
            return (( (0.55)*(input-32.0f) ).toFloat()+273.15).toFloat()
        }else{
            return 0.0f
        }
    }
    fun initSpinner(){
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, conversions)
        binding.conversionList.adapter = adapter
    }
}