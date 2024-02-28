package ru.easycode.zerotoheroandroidtdd


/*
class EditFolderPage {

    private val rootId: Int = R.id.editFolderRootLayout

    private val inputView = onView(
        allOf(
            isAssignableFrom(TextInputEditText::class.java),
            withId(R.id.folderEditText),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withParent(withId(rootId))
        )
    )

    fun checkVisibleNow(text: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.editFolderTitleTextView),
                withText("rename folder"),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId))
            )
        ).check(matches(isDisplayed()))
        inputView.check(matches(withText(text)))
    }

    fun replaceText(text: String) {
        inputView.perform(clearText(), typeText(text), closeSoftKeyboard())
    }

    fun clickSaveButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(Button::class.java),
                withId(R.id.saveFolderButton),
                withText("save")
            )
        ).perform(click())
    }

    fun checkNotVisibleNow() {
        inputView.check(doesNotExist())
    }

    fun clickDeleteButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(Button::class.java),
                withId(R.id.deleteFolderButton),
                withText("delete")
            )
        ).perform(click())
    }
}

 */