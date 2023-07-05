package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

import java.lang.Exception
import javax.xml.xpath.XPathExpression
import kotlin.ArithmeticException
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var lastNumeric=false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



    fun onEqualClick(view: View) {
        onEqual()
        binding.datatv.text=binding.text1.text.toString().drop(1)
    }


    fun onDigitClick(view: View) {
        if(stateError){
            binding.datatv.text=(view as Button).text
             stateError=false
        }else{
            binding.datatv.append((view as Button).text)
        }
        lastNumeric=true
        onEqual()


    }


    fun onAllClearClick(view: View) {
        binding.datatv.text=""
        binding.text1.text=""
        stateError=false
        lastDot=false
        lastNumeric=false
        binding.text1.visibility=View.GONE
    }


    fun onOperatorClick(view: View) {
        if(lastNumeric && !stateError){
            binding.datatv.append((view as Button).text)
            lastNumeric=false
            lastDot=false
            onEqual()
        }
    }


    fun onBackClick(view: View) {
         binding.datatv.text=binding.datatv.text.toString().dropLast(1)
        try{
            val lastChar =binding.datatv.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e:Exception){
            binding.text1.text=""
            binding.text1.visibility=View.GONE
            Log.e("last char error",e.toString())
        }
    }

    fun onEqual(){
        if(lastNumeric && !stateError){
            val text=binding.datatv.text.toString()
            expression=ExpressionBuilder(text).build()
            try{
                val result = expression.evaluate()
                binding.text1.visibility=View.VISIBLE
                binding.text1.text="=" + result.toString()
            }catch (ex : ArithmeticException){

                Log.e("evaluate error",ex.toString())
                binding.text1.text="Error"
                stateError=true
                lastNumeric=false
            }
        }
    }


    fun onClearClick(view: View) {
        binding.datatv.text=""
        lastNumeric=false
    }
}