package com.example.finalproject_cthru.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE = "image"

        fun newInstance(title: String, description: String, @DrawableRes image: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_IMAGE, image)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        val title = arguments?.getString(ARG_TITLE)
        val description = arguments?.getString(ARG_DESCRIPTION)
        val imageResId = arguments?.getInt(ARG_IMAGE) ?: 0

        // Assuming you have TextViews and ImageView in your fragment layout to display these
        view.findViewById<TextView>(R.id.onboarding_title).text = title
        view.findViewById<TextView>(R.id.onboarding_description).text = description
        view.findViewById<ImageView>(R.id.onboarding_image).setImageResource(imageResId)

        return view
    }
}