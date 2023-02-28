package com.adlemgames.pbk

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType


class Intro : AppIntro() { // главное интро
    lateinit var mySharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStatusBar(true)

        // Make sure you don't call setContentView!
        //isColorTransitionsEnabled = true
        mySharedPreferences = getSharedPreferences("intro", Context.MODE_PRIVATE)
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        setColorSkipButton(resources.getColor(R.color.text, application.theme))
        setColorDoneText(resources.getColor(R.color.text, application.theme))
        when (baseContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> setIndicatorColor(Color.GRAY, Color.DKGRAY)
            else -> setIndicatorColor(Color.DKGRAY, Color.GRAY)
        }

        setBackArrowColor(resources.getColor(R.color.text, application.theme))
        setNextArrowColor(resources.getColor(R.color.text, application.theme))

        setTransformer(
            AppIntroPageTransformerType.Zoom)
        addSlide(
            AppIntroFragment.newInstance(
                title = "Помощник в бинарном коде",
                imageDrawable = R.drawable.icon,
                titleColor = resources.getColor(R.color.text, application.theme),
                descriptionColor = resources.getColor(R.color.text, application.theme),
                description = "Данное приложение поможет Вам изучить и научиться пользоваться двоичными системами.\n" +
                        "Изучайте теорию и выполняйте интерактивные задания"
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Вводите необходимые Вам числа в калькулятор.",
            description = "Калькулятор переводит в выбранную систему счисления и демонстрирует весь процесс.\n\n",
            titleColor = resources.getColor(R.color.text, application.theme),
            descriptionColor = resources.getColor(R.color.text, application.theme),
            imageDrawable = R.drawable.ic_baseline_calc_500,
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Таблицы истинности",
                imageDrawable = R.drawable.ic_baseline_table_chart_500,
            descriptionColor = resources.getColor(R.color.text, application.theme),
                description = "Кроме этого наше приложение поможет построить таблицы истинности и логические схемы.",
            titleColor = resources.getColor(R.color.text, application.theme)
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        val editor: SharedPreferences.Editor = mySharedPreferences.edit()
        editor.putBoolean("showed", true)
        editor.apply()
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        val editor: SharedPreferences.Editor = mySharedPreferences.edit()
        editor.putBoolean("showed", true)
        editor.apply()
        finish()
    }
}