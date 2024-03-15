package uk.ac.aber.dcs.cs31620.healthypaws.datasource

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.healthypaws.model.*
import java.time.LocalDate
import java.time.LocalTime

@Database(entities = [Dog::class, Exercise::class, Diet::class, CalendarEvent::class], version = 2)
abstract class HealthyPawsRoomDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun dietDao(): DietDao
    abstract fun calendarEventDao(): CalendarEventDao

    companion object {
        private var instance: HealthyPawsRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): HealthyPawsRoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<HealthyPawsRoomDatabase>(
                        context.applicationContext,
                        HealthyPawsRoomDatabase::class.java,
                        "healthy_paws_database"
                    )
//                        .allowMainThreadQueries()
                        .addCallback(roomDatabaseCallback(context))
//                        .addMigrations(MIGRATION_1_2)
                        .build()
            }
            return instance
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    coroutineScope.launch {
                        populateDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }


        private suspend fun populateDatabase(context: Context, instance: HealthyPawsRoomDatabase) {
            val sitExercise = Exercise(
                0,
                "Sit",
                Difficulty.EASY,
                "Teaching your dog to sit is one of the most basic dog commands to teach your pup.",
                listOf(
                    "Hold a treat close to your dog’s nose.;",
                    "Move your hand up, allowing his head to follow the treat and causing his bottom to lower.;",
                    "Once he’s in sitting position, say “Sit,” give him the treat, and share affection.;"
                ),
                listOf(
                    "file:///android_asset/images/sit2.png",
                    "file:///android_asset/images/sit1.png",
                    "file:///android_asset/images/sit3.png"
                ),
                "file:///android_asset/images/dog_sit.png"
            )
            val rightPawExercise = Exercise(
                0,
                "Right Paw",
                Difficulty.EASY,
                "Teach your dog to offer his paw for a shake or high-five",
                listOf(
                    "Place your dog in a sitting position.;",
                    "Hold a treat close to your dog’s nose to get his attention.;",
                    "Move the treat slowly towards his paw while saying \"Paw.\";"
                ),
                listOf(
                    "file:///android_asset/images/rightpaw1.png",
                    "file:///android_asset/images/rightpaw2.png",
                    "file:///android_asset/images/rightpaw3.png"
                ),
                "file:///android_asset/images/dog_right_paw.png"
            )
            val lieDownExercise = Exercise(
                0,
                "Down",
                Difficulty.EASY,
                "Instruct your dog to lay down on the ground.",
                listOf(
                    "Start with your dog in a sitting position.;",
                    "Hold a treat close to your dog’s nose and slowly move it down towards the ground.;",
                    "As your dog follows the treat down, say \"Lie down\" in a firm but gentle tone.;"
                ),
                listOf(
                    "file:///android_asset/images/down2.png",
                    "file:///android_asset/images/down1.png",
                    "file:///android_asset/images/down3.png"
                ),
            "file:///android_asset/images/dog_lie_down.png"
            )
            val begForExercise = Exercise(
                0,
                "Beg for",
                Difficulty.EASY,
                "Teach your dog to stand on his hind legs and \"beg\" for a treat or attention.",
                listOf(
                    "Start with your dog in a sitting position.;",
                    "Hold a treat in front of your dog's nose and slowly raise it above his head.;",
                    "As your dog reaches up towards the treat, say \"Beg\" and give him the treat as a reward.;"
                ),
                listOf(
                    "file:///android_asset/images/begfor2.png",
                    "file:///android_asset/images/begfor1.png",
                    "file:///android_asset/images/begfor3.png"
                ),
                "file:///android_asset/images/dog_beg_for.png"
            )
            val comeHereExercise = Exercise(
                0,
                "Come here",
                Difficulty.EASY,
                "Call your dog to come to you.",
                listOf(
                    "Use a happy and enthusiastic tone of voice to say \"Come here\" or use your dog's name.;",
                    "Take a step back and encourage your dog to follow you with a treat or toy.;",
                    "When your dog reaches you, give him a reward and praise him for a job well done.;"
                ),
                listOf(
                    "file:///android_asset/images/call1.png",
                    "file:///android_asset/images/call3.png",
                    "file:///android_asset/images/call2.png"
                ),
                "file:///android_asset/images/dog_come_here.png"
            )
            val stayExercise = Exercise(
                0,
                "Stay",
                Difficulty.EASY,
                "Teach your dog to remain in place until released.",
                listOf(
                    "Start with your dog in a sitting or down position.;",
                    "Hold your hand up in a \"stop\" gesture and say \"Stay\" in a firm but calm voice.;",
                    "Take a step back, count to three, then return to your dog and give him a reward.;"
                ),
                listOf(
                    "file:///android_asset/images/stay3.png",
                    "file:///android_asset/images/stay1.png",
                    "file:///android_asset/images/stay2.png"
                ),
                "file:///android_asset/images/dog_stay.png"
            )
            val fetchExercise = Exercise(
                0,
                "Fetch",
                Difficulty.EASY,
                "Teach your dog to retrieve an object and bring it back to you.",
                listOf(
                    "Hold your dog's favorite toy or object and say \"Fetch\" in an enthusiastic tone of voice.;",
                    "Throw the object a short distance away from you.;",
                    "Encourage your dog to retrieve the object and bring it back to you. Reward your dog with praise and a treat.;"
                ),
                listOf(
                    "file:///android_asset/images/fetch3.png",
                    "file:///android_asset/images/fetch1.png",
                    "file:///android_asset/images/fetch2.png"
                ),
                "file:///android_asset/images/dog_fetch.png"
            )
            val searchExercise = Exercise(
                0,
                "Search",
                Difficulty.EASY,
                "Teach your dog to find a specific object or person.",
                listOf(
                    "Start by hiding a favorite toy or treat in plain sight.;",
                    "Encourage your dog to find the hidden object or treat using the command \"Search.\";",
                    "Once your dog has found the object, reward him with praise and a treat.;"
                ),
                listOf(
                    "file:///android_asset/images/search3.png",
                    "file:///android_asset/images/search1.png",
                    "file:///android_asset/images/search2.png"
                ),
                "file:///android_asset/images/dog_search.png"
            )
            val goForwardExercise = Exercise(
                0,
                "Go forward",
                Difficulty.EASY,
                "Teach your dog to move forward on command.",
                listOf(
                    "Begin by placing your dog in a standing position.;",
                    "Hold a treat or toy in front of your dog's nose and say \"Go forward\" in an encouraging tone.;",
                    "Take a step forward and encourage your dog to follow you with the treat or toy. Reward your dog with praise and a treat.;"
                ),
                listOf(
                    "file:///android_asset/images/forward3.png",
                    "file:///android_asset/images/forward1.png",
                    "file:///android_asset/images/forward2.png"
                ),
                "file:///android_asset/images/dog_go_forward.png"
            )
            val jumpExercise = Exercise(
                0,
                "Jump",
                Difficulty.EASY,
                "Teach your dog to jump on command.",
                listOf(
                    "Start by placing a low obstacle, such as a hurdle or broomstick, in front of your dog.;",
                    "Encourage your dog to jump over the obstacle by holding a treat or toy on the other side and saying \"Jump.\";",
                    "Reward your dog with praise and a treat when he successfully jumps over the obstacle.;",
                ),
                listOf(
                    "file:///android_asset/images/jump1.png",
                    "file:///android_asset/images/jump2.png",
                    "file:///android_asset/images/jump3.png"
                ),
                "file:///android_asset/images/dog_jump.png"
            )
            val rollOverExercise = Exercise(
                0,
                "Roll over",
                Difficulty.MEDIUM,
                "Teach your dog to roll over from his belly to his back and back to his belly.",
                listOf(
                    "Start with your dog in a down position.;",
                    "Hold a treat close to your dog's nose and slowly move it towards his shoulder, encouraging him to roll onto his side.;",
                    "As your dog rolls onto his side, continue to move the treat towards his back, encouraging him to roll over onto his other side and onto his belly again.;"
                ),
                listOf(
                    "file:///android_asset/images/roll1.png",
                    "file:///android_asset/images/roll2.png",
                    "file:///android_asset/images/roll3.png"
                ),
                "file:///android_asset/images/dog_roll_over.png"
            )
            val catchExercise = Exercise(
                0,
                "Catch",
                Difficulty.EASY,
                "Teach your dog to catch an object in his mouth.",
                listOf(
                    "Start by tossing a small, soft object, such as a ball or toy, towards your dog.;",
                    "Encourage your dog to catch the object by saying \"Catch.\";",
                    "Reward your dog with praise and a treat when he successfully catches the object.;"
                ),
                listOf(
                    "file:///android_asset/images/catch3.png",
                    "file:///android_asset/images/catch2.png",
                    "file:///android_asset/images/catch1.png"
                ),
                "file:///android_asset/images/dog_catch.png"
            )
            val bellyExercise = Exercise(
                0,
                "Belly",
                Difficulty.HARD,
                "Teach your dog to lie on his back with his belly upwards.",
                listOf(
                    "Start with your dog in a lying down position.;",
                    "Hold a treat close to your dog's nose and slowly move it towards his belly.;",
                    "Encourage him to roll onto his back. Say \"Belly\" and reward your dog with praise and a treat when he successfully lies on his back with his belly upwards.;"
                ),
                listOf(
                    "file:///android_asset/images/belly1.png",
                    "file:///android_asset/images/belly2.png",
                    "file:///android_asset/images/belly3.png"
                ),
                "file:///android_asset/images/dog_belly.png"
            )
            val spinExercise = Exercise(
                0,
                "Spin",
                Difficulty.EASY,
                "Teach your dog to spin in a circle.",
                listOf(
                    "Start with your dog in a standing position.;",
                    "Hold a treat or toy in front of your dog's nose and move it in a circular motion around his head.;",
                    "As your dog follows the treat or toy in a circular motion, say \"Spin\" and encourage him to complete the circle. Reward your dog with praise and a treat.;"
                ),
                listOf(
                    "file:///android_asset/images/spin3.png",
                    "file:///android_asset/images/spin2.png",
                    "file:///android_asset/images/spin1.png"
                ),
                "file:///android_asset/images/dog_spin.png"
            )
            val highFiveExercise = Exercise(
                0,
                "High five",
                Difficulty.HARD,
                "Teach your dog to give you a high five.",
                listOf(
                    "Start with your dog in a sitting position.;",
                    "Hold a treat or toy in front of your dog's nose and raise it up towards your hand.;",
                    "As your dog reaches up to touch your hand with his paw, say \"High five\" and reward him with the treat or toy.;"
                ),
                listOf(
                    "file:///android_asset/images/high3.png",
                    "file:///android_asset/images/high2.png",
                    "file:///android_asset/images/high1.png"
                ),
                "file:///android_asset/images/dog_high_five.png"
            )
            val speakExercise = Exercise(
                0,
                "Speak",
                Difficulty.EASY,
                "Teach your dog to bark on command.",
                listOf(
                    "Start by getting your dog excited, such as by playing with a toy or running around.;",
                    "Once your dog starts to bark, say \"Speak\" and reward him with a treat.;",
                    "Practice the command with increasing levels of excitement until your dog can bark on command without needing to be excited beforehand.;"
                ),
                listOf(
                    "file:///android_asset/images/speak1.png",
                    "file:///android_asset/images/speak2.png",
                    "file:///android_asset/images/speak3.png"
                ),
                "file:///android_asset/images/dog_speak.png"
            )
            val whisperExercise = Exercise(
                0,
                "Whisper",
                Difficulty.MEDIUM,
                "Teach your dog to bark softly on command.",
                listOf(
                    "Start with your dog in a quiet environment.;",
                    "Hold a treat close to your dog's nose and say \"Whisper\" in a soft, calm voice.;",
                    "Encourage your dog to bark softly by speaking in a soft, calm voice. Reward your dog with praise and a treat when he barks softly.;"
                ),
                listOf(
                    "file:///android_asset/images/whisper1.png",
                    "file:///android_asset/images/whisper2.png",
                    "file:///android_asset/images/whisper3.png"
                ),
                "file:///android_asset/images/dog_whisper.png"
            )
            val crawlExercise = Exercise(
                0,
                "Crawl",
                Difficulty.MEDIUM,
                "Teach your dog to crawl on his belly.",
                listOf(
                    "Start with your dog in a lying down position.;",
                    "Hold a treat in front of your dog's nose and slowly move it away from him, towards his feet.;",
                    "As your dog follows the treat, say \"Crawl\" and encourage him to move forward on his belly. Reward your dog with praise and a treat as he crawls.;"
                ),
                listOf(
                    "file:///android_asset/images/crawl3.png",
                    "file:///android_asset/images/crawl2.png",
                    "file:///android_asset/images/crawl1.png"
                ),
                "file:///android_asset/images/dog_crawl.png"
            )

            val exerciseList = mutableListOf(
                sitExercise,
                rightPawExercise,
                lieDownExercise,
                begForExercise,
                comeHereExercise,
                stayExercise,
                fetchExercise,
                searchExercise,
                goForwardExercise,
                jumpExercise,
                rollOverExercise,
                catchExercise,
                bellyExercise,
                spinExercise,
                highFiveExercise,
                speakExercise,
                whisperExercise,
                crawlExercise
            )

            val exerciseDao = instance.exerciseDao()
            exerciseDao.insertMultipleExercises(exerciseList)

            val dietOne = Diet(
                0,
                "Breakfast",
                "Bowl of dry meat and vegetable chunks."
            )

            val dietTwo = Diet(
                0,
                "Breakfast",
                "Sandwiches with egg and a little bit of cheese spread."
            )

            val dietThree = Diet(
                0,
                "Lunch",
                "Soup with meat broth, grains, and meat chunks."
            )

            val dietFour = Diet(
                0,
                "Lunch",
                "Salad with vegetables and diced chicken or turkey."
            )

            val dietFive = Diet(
                0,
                "Dinner",
                "Ground meat with rice and cooked vegetables."
            )

            val dietSix = Diet(
                0,
                "Dinner",
                "Pancakes with chicken and spinach filling."
            )

            val dietSeven = Diet(
                0,
                "Supper",
                "Salmon fillet with cooked carrots."
            )

            val dietEight = Diet(
                0,
                "Supper",
                "Salad with cooked chicken, diced apples, and mixed greens."
            )

            val dietList = mutableListOf(
                dietOne,
                dietTwo,
                dietThree,
                dietFour,
                dietFive,
                dietSix,
                dietSeven,
                dietEight
            )

            val dietDao = instance.dietDao()
            dietDao.insertMultipleDiets(dietList)

            val billyDog = Dog(
                0,
                "Billy",
                "Dachshund",
                LocalDate.of(2020, 5, 5),
                setOf(),
                "file:///android_asset/images/billy_dog.png"
            )

            val dogList = mutableListOf(
                billyDog
            )

            val dogDao = instance.dogDao()
            dogDao.insertMultipleDogs(dogList)

            val eventOne = CalendarEvent(
                0,
                "Veterinary",
                LocalDate.of(2023, 4, 22),
                LocalTime.of(13, 30)
            )

            val eventTwo = CalendarEvent(
                0,
                "Groomer",
                LocalDate.of(2023, 4, 27),
                LocalTime.of(13, 30)
            )

            val eventThree = CalendarEvent(
                0,
                "Training",
                LocalDate.of(2023, 4, 30),
                LocalTime.of(13, 30)
            )

            val eventFour = CalendarEvent(
                0,
                "Vaccine",
                LocalDate.of(2023, 5, 15),
                LocalTime.of(13, 30)
            )

            val eventList = mutableListOf(
                eventOne,
                eventTwo,
                eventThree,
                eventFour
            )

            val eventDao = instance.calendarEventDao()
            eventDao.insertMultipleEvents(eventList)
        }
    }
}