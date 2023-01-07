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


class BlockTutorial : AppIntro() {
    lateinit var mySharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStatusBar(true)

        // Make sure you don't call setContentView!
        //isColorTransitionsEnabled = true
        mySharedPreferences = getSharedPreferences("block_tutorial", Context.MODE_PRIVATE)
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
            AppIntroPageTransformerType.Fade)
        addSlide(
            AppIntroFragment.newInstance(
                title = "Конструтор схем",
                imageDrawable = R.drawable.and,
                titleColor = resources.getColor(R.color.text, application.theme),
                descriptionColor = resources.getColor(R.color.text, application.theme),
                description = "Чтобы понять как пользоваться конструктором схем пройдите короткое обучение!"
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Добавление",
            description = "Чтобы добавить блоки в рабочую область - зажмите на пару секунд нужный блок, а затем перенесите его на экран\n\n",
            titleColor = resources.getColor(R.color.text, application.theme),
            descriptionColor = resources.getColor(R.color.text, application.theme),
            imageDrawable = R.drawable.selector,
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "Входы",
            imageDrawable = R.drawable.input,
            descriptionColor = resources.getColor(R.color.text, application.theme),
            description = "Чтобы поменять состояния блоков есть специальный элемент - вход, чтобы менять его состояние нажмите на центр элемента\n",
            titleColor = resources.getColor(R.color.text, application.theme)
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Соединение",
                imageDrawable = R.drawable.connection,
            descriptionColor = resources.getColor(R.color.text, application.theme),
                description = "Чтобы соединить блоки сначала нажмите на один блок, затем на другой\n" +
                        "Готово!",
            titleColor = resources.getColor(R.color.text, application.theme)
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Удаление",
                imageDrawable = R.drawable.delete,
            descriptionColor = resources.getColor(R.color.text, application.theme),
                description = "Если вы хотите удалить блок, то просто перетащите его в верхний правый угол\n",
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