public class Main {
    public static void main(String[] args){
        Generator generator = new Generator();
        generator.getFiles("D:/text1/", 3, 1000 * 1000, null, 60);
        String[] word = new String[]{
                "dog",
                "puppy",
                "cat",
                "kitten",
                "parrot",
                "hamster",
                "chinchilla",
                "mouse",
                "rat",
                "chicken",
                "rooster",
                "cow",
                "bull",
                "piglet",
                "turkey",
                "sheep",
                "ram",
                "lamb",
                "horse",
                "rabbit",
                "goat",
                "donkey",
                "Fly",
                "Spider",
                "Bee",
                "Scorpion",
                "Wasp",
                "Mosquito",
                "Beetle",
                "Crocodile",
                "Turtle",
                "Snake",
                "Rattlesnake",
                "Cobra",
                "Dinosaur"};
        generator.getFiles("D:/text2/", 10, 1000 * 1000 * 3, word, 100);// 3 МБ
    }
}
