import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {
    private String color;
    private List<Piece> pieceList;
    private Stack<Piece> graveyard;
    private boolean underCheck;
    private void initializePieces() {
		pieceList.add(new King(this, color));
		pieceList.add(new Queen(this, color));
		for(int i = 0; i < 2; i++) {
			pieceList.add(new Rook(this, color));
			pieceList.add(new Bishop(this, color));
			pieceList.add(new Knight(this, color));
		}
		for(int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(this, color));
		}
	}
    public void initialize(String color) {
		pieceList = new ArrayList<Piece>();
		graveyard = new Stack<Piece>();
		this.color = color;
		this.initializePieces();
	}
	
    public String getColor() {
		return color;
	}
	
	public List<Piece> getPieceList() {
		return pieceList;
	}
	
	public void addToGraveyard(Piece piece) {
		try {
            // Open an audio input stream from the specified file
			Display.audioInputStreamVar=AudioSystem.getAudioInputStream(new File("src/audio/capture.wav").getAbsoluteFile());
            
        } catch (UnsupportedAudioFileException | IOException err) {
            err.printStackTrace();
        }
		graveyard.push(piece);
		removePiece(piece);
	}
	
	public Stack<Piece> getGraveyard() {
		return graveyard;
	}
	
	public void removePiece(Piece piece) {
		pieceList.remove(piece);
	}
	
	public boolean underCheck() {
		return underCheck;
	}
	
	public void setCheck(boolean underCheck) {
		this.underCheck = underCheck;
	}
}
