package com.manuel.katlin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResultado = findViewById(R.id.txtResultado)
        for (i in 1..totalCeldas){
            var boton = findViewById<Button>(resources.getIdentifier("btn$i", "id", packageName))
            boton.setOnClickListener(this)
            btns[i-1] = boton
        }
    }



    override fun onClick(view: View?) {
        btnSeleccionado(view as Button)
    }
    private fun btnSeleccionado(boton : Button){
        var indice = 0
        when(boton.id){
            R.id.btn1 -> indice = 0
            R.id.btn2 -> indice = 1
            R.id.btn3 -> indice = 2
            R.id.btn4 -> indice = 3
            R.id.btn5 -> indice = 4
            R.id.btn6 -> indice = 5
            R.id.btn7 -> indice = 6
            R.id.btn8 -> indice = 7
            R.id.btn9 -> indice = 8
        }
        jugar(indice, boton)
        checkGanador()
        actualizar()
    }
    private fun jugar(indice : Int, boton : Button){

        if(!ganador.isNullOrEmpty()){
            Toast.makeText(this, "¡JUEGO FINALIZADO!", Toast.LENGTH_SHORT).show()
            return
        }

        when{
            esX -> celdas[indice] = x
            else -> celdas[indice] = o
        }

        boton.text = celdas[indice]
        boton.isEnabled = false
        esX = !esX
    }

    fun reinicar(view: View?){
        nuevoJuego()
    }
    private fun nuevoJuego(){
        celdas = mutableMapOf()
        esX = true
        ganador = ""
        txtResultado.text = resources.getString(R.string.sig_jugador, x)
        txtResultado.setTextColor(Color.BLACK)
        botonReinicio()
    }
    private fun botonReinicio(){
        for (i in 1..totalCeldas){
            var boton = btns[i-1]
            boton?.text = ""
            boton?.isEnabled = true
        }
    }

    private fun checkGanador(){
        if(celdas.isNotEmpty()){
            for(combinacion in combinaciones){
                var(a, b, c) = combinacion
                    if(celdas[a]!=null && celdas[a] == celdas[b] && celdas[a] == celdas[c]){
                        this.ganador = celdas[a].toString()
                    }
            }
        }
    }

    private fun actualizar(){
        when{
            ganador.isNotEmpty() -> {
                txtResultado.text = resources.getString(R.string.ganador, ganador)
                txtResultado.setTextColor(Color.GREEN)
            }
            celdas.size == totalCeldas -> {
                txtResultado.text = "EMPATE"
            }
            else -> {
                txtResultado.text = resources.getString(R.string.sig_jugador, if (esX)x else o)
            }
        }
    }


    

    private var celdas = mutableMapOf<Int, String>()
    private var esX = true
    private var ganador : String = ""
    private val totalCeldas = 9
    private lateinit var txtResultado : TextView
    private val x = "X"
    private val o = "O"
    private var btns = arrayOfNulls<Button>(totalCeldas)
    private val combinaciones : Array<IntArray> = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
    )
}
