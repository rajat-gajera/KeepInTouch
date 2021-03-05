package com.example.keepintouch.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepintouch.Adapter.SurvivalKitAdapter;
import com.example.keepintouch.Model.SurvivalKitItem;
import com.example.keepintouch.R;

import java.util.ArrayList;


public class SurvivalKitActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private SurvivalKitAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_servival_kit,container,false);
        getActivity().setTitle("Servival Kit");
        ArrayList<SurvivalKitItem> survivalKitItemList =new ArrayList<>();
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_master_your_attitude,"1.Master your attitude","A survival situation is not the time to panic. You are more likely to survive a difficult situation if you focus on maintaining a positive, proactive attitude.\n\n ● Develop a plan.\n\n ● Inventory the resources you have.\n\n ● Identify the critical tasks required for survival(Water, shelter,warmth).\n\n ● Determination: It's often grit that separate a survivor from a non-survivor.\n\n ● Recognise feelings are not facts you may feel hopeless, but keep your thoughts focused on the task that need to be accomplished.\n" ));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_insulated_shelter,"2.Make an insulated shelter","Building an effective shelter can help protect you from hypothermia and the elements\n\n ● Think small: Since your body heat will be your primary source of warmth,build a shelter just big enough to accommodate your body when lying down.\n\n ● Construct the framework: To make a simple learn-to, use available resources, such a fallen tree or a strong branch securely against a standing tree.\n\n ● At the sides: Stack sticks close together on one side. Use progressively smaller sticks to fill in gaps.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_shade_shelter,"3.Make a shade shelter","In some situations, Protection from heat will matter most.\n \n● Think cool: Digging just a few inches in the soil can uncover cooler ground.\n\n ● Built a lean-to: Use sticks for limbs to make a shelter over the exposed ground.\n\n ● Let the airflow: The purpose of this shelter is to create shared. Use available material such as Bark, leaves, a poncho, an emergency sleeping bag or blanket or any available fabric to cover one side.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic__photo_water,"4.Find clean water","Finding clean, uncontaminated water is the holy grail of survival.\n\n ● Rain: Collect, store and drink.\n \n● Snow: The energy it requires for your body to absorb the water from snow is high. Instead of eating the snow, melt it first. This can easily be done over a fire or with a camp stove. If those aren't options, use the sun. Accelerate the process by dropping up ice and hanging it in the water bag in direct sunlight. If there is no sun use your body's heat.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_water_resource,"5.Find other water sources","Boiling water for a minute is the best and safest way to kill off any pathogens.\n\n ● Digging for water: Certain plants indicate water sources are nearby. Identify plants, such as cattails, cottonwood or willows, and dig a seep hole until you reach moisture. Wait for water to collect in the hole.\n\n ● Think topographically: Rock outcropping, or identations are likely areas for water to accumulate. Remember, water found in puddles or streams should be boiled.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_water_vegitable,"6.Collect water from vegetation","● Dew: Dew collects on plants and grasses. Using a cloth or piece of clothing soak up the dew and then squeeze it into a container. This can be very effective method of collecting a considerable amount of water.\n\n ● Plant moisture Bag: just like humans, plants sweat. Tie a plastic bag around a leafy branch of a tree, and over time water will collect."));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_light_fire,"7.Light a fire","You will want to practice alternative method of fire starting prior to when they are needed\n\n ● Easy: Use lighter or waterproof matches. Keep your matches dry in a waterproof container.\n\n ● Medium: Use magnesium fire starter. Shave magnesium filings of the stick, use the back of your knife to create a spark and ignite the filings."));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_build_fire,"8.Bulid a fire","● Create a tinder bundle: Gather pine needles, dry leaves, milkweed or thistle down and dry grass for tinder.\n\n ● Start small: Gather small, dry sticks for kindling.\n\n ● Go big: Find larger piece of wood for long burning fuel.\n \n● Put it together: Using a larger piece of wood as a wind block, create a nest out of the tinder. Create a tipi out of smaller kinding so oxygen can get in. Ignite the tinder and placed under the tepee. Use long, steady breaths to spread the flame. As the smaller pieces catch, add progressively larger fuel to the fire.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_knots,"9.Know these knots","All outdoor people should know a variety of knots. When it comes to Survival, make sure you have these two at the ready.\n\n ● Bowline: This knot is extremely useful when you need to attach something to a rope via loop, because the tighter you pull, the tighter the knot gets. After you make a loop, remember this: The rabbit comes out of the hole, in front of the tree, goes behind the tree, and back down its original hole.\n\n ● Double half hitch: Used to attach one end of the rope around an object. This is a useful knot for building a shelter. Tie a half hitch around your object, like a tree or pole, and follow it by second in the same direction to make it a double. Pull tight to Make secure.\n"));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_spear,"10.Make a spear","Without simple spear, you can improve your odds of catching a fish or other small game.\n\n ● Select a long, straight stick.\n \n● Split the end of the stick to create a fork.\n\n ● Separate the fork with wooden wedge or small stone. Lash it into place.\n\n ● Sharpen each fork with a knife or sharp rock.\n\n To make a triple-prong spear, add a smaller stick after placing the wedge, sharpen, and lash it into place."));
        survivalKitItemList.add(new SurvivalKitItem(R.drawable.ic_photo_gears,"5 pieces of gear to have","When it comes to Survival gear, told that can solve more than one purpose are best.\n\n 1. Lighter: Bic($4), Coghalan's Magnesium Starter ($7.99) for backup; tea light candle ($1)-lighter is cheap and stays dry to light tinder.Having a backup fire starter is essential.\n\n 2. Cell phone: Though you might not always get a signal, it has become the essential modern survival tool. Carry an extra battery for an external charger as well.\n\n 3. Hydration bag: MSR Dromendary Bag 10-L($44.95) - Though the MSR Dromedary hydration bag comes in several sizes, going with the 10-L allows you to flexibility to vary your volume, depending on your current need. What sets this bag apart from others is its tough outer shell, which protects against abrasions and leakage.\n\n 4. Folding knife: The TecX Inceptra fold-up knife($24.99) is secure and sharp. This inexpensive fold-up knife is lightweight and conveniently folds for safety. Also, it easily shaves wood for kindling or serves as spear point\n\n 5.Emergency shelter: Adventure medical kits' S.O.L. thermal Bivvy($29) is lightweight and compact. This waterproof thermal sack will help you retain your body heat and avoid skin exposure to cold air\n"));
        mRecyclerView= rootview.findViewById(R.id.fragment_survivalkit_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(getContext());
        mAdapter= new SurvivalKitAdapter(survivalKitItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootview;

    }
}
