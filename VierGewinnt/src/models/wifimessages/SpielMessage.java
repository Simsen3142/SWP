package models.wifimessages;

public class SpielMessage extends WifiMessage {

	public SpielMessage(String sender, String type) {
		super(sender, type);
	}
	
	public static class BackMessage extends SpielMessage {
		public BackMessage(String sender) {
			super(sender, "game_back");
		}
	}
	
	public static class ForwardMessage extends SpielMessage {
		public ForwardMessage(String sender) {
			super(sender, "game_forward");
		}
	}
	
	public static class ResetMessage extends SpielMessage {
		public ResetMessage(String sender) {
			super(sender, "game_reset");
		}
	}
	
	public static class AddChipMessage extends SpielMessage {
		private int column;
		private int row;
		
		/**
		 * @return the column
		 */
		public int getColumn() {
			return column;
		}
		
		/**
		 * @return the row
		 */
		public int getRow() {
			return row;
		}

		public AddChipMessage(String sender, int column, int row) {
			super(sender, "game_add");
			this.column=column;
			this.row=row;
		}
	}
	
	public static class RemoveChipMessage extends SpielMessage {
		private int column;
		private int row;
		
		/**
		 * @return the column
		 */
		public int getColumn() {
			return column;
		}
		
		/**
		 * @return the row
		 */
		public int getRow() {
			return row;
		}

		public RemoveChipMessage(String sender, int column, int row) {
			super(sender, "game_remove");
			this.column=column;
			this.row=row;
		}
	}

}
