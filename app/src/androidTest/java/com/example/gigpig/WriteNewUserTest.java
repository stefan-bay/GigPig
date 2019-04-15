package com.example.gigpig;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WriteNewUserTest {

    @Test
    public void writeNewUserTest() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("space");
        tags.add("earth");
        tags.add("astrophysics");
        tags.add("physics");
        tags.add("big");

        User niel = new User("Niel",
                "Tyson",
                "719-548-1212",
                "spaceguy89",
                tags,
                "Astrophysicist" +
                        "\n" +
                        "\n" +
                        "Looking for some side astrophysics work" +
                        "I am good at space stuff: peep my wikipedia--" +
                        "\n" +
                        "\n" +
                        "Neil deGrasse Tyson (/dəˈɡræs/; born October 5, 1958) is an American astrophysicist, author, and science communicator. Since 1996, he has been the Frederick P. Rose Director of the Hayden Planetarium at the Rose Center for Earth and Space in New York City. The center is part of the American Museum of Natural History, where Tyson founded the Department of Astrophysics in 1997 and has been a research associate in the department since 2003. " +
                        "\nTyson studied at Harvard University, the University of Texas at Austin and Columbia University. From 1991 to 1994 he was a postdoctoral research associate at Princeton University. In 1994, he joined the Hayden Planetarium as a staff scientist and the Princeton faculty as a visiting research scientist and lecturer. In 1996, he became director of the planetarium and oversaw its $210 million reconstruction project, which was completed in 2000. " +
                        "\nFrom 1995 to 2005, Tyson wrote monthly essays in the \"Universe\" column for Natural History magazine, some of which were later published in his books Death by Black Hole (2007) and Astrophysics for People in a Hurry (2017). During the same period, he wrote a monthly column in StarDate magazine, answering questions about the universe under the pen name \"Merlin\". Material from the column appeared in his books Merlin's Tour of the Universe (1998) and Just Visiting This Planet (1998). Tyson served on a 2001 government commission on the future of the U.S. aerospace industry, and on the 2004 Moon, Mars and Beyond commission. He was awarded the NASA Distinguished Public Service Medal in the same year. From 2006 to 2011, he hosted the television show NOVA ScienceNow on PBS. Since 2009, Tyson has hosted the weekly podcast StarTalk. A spin-off, also called StarTalk, began airing on National Geographic in 2015. In 2014, he hosted the television series Cosmos: A Spacetime Odyssey, a successor to Carl Sagan's 1980 series Cosmos: A Personal Voyage.[1] The U.S. National Academy of Sciences awarded Tyson the Public Welfare Medal in 2015 for his \"extraordinary role in exciting the public about the wonders of science.\"");

        DatabaseHelper.writeNewUser(niel);
    }
}
