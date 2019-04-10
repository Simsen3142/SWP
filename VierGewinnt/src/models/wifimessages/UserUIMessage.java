package models.wifimessages;

public class UserUIMessage extends WifiMessage {

	public UserUIMessage(String sender, String type) {
		super(sender, type);
	}
	
	public static class BackMessage extends UserUIMessage {
		public BackMessage(String sender) {
			super(sender, "ui_back");
		}
	}
	
	public static class ForwardMessage extends UserUIMessage {
		public ForwardMessage(String sender) {
			super(sender, "ui_forward");
		}
	}
	
	public static class ResetMessage extends UserUIMessage {
		public ResetMessage(String sender) {
			super(sender, "ui_reset");
		}
	}
	
	public static class ColumnClickedMessage extends UserUIMessage {
		private int column;
		
		/**
		 * @return the column
		 */
		public int getColumn() {
			return column;
		}

		public ColumnClickedMessage(String sender, int column) {
			super(sender, "ui_clicked");
			this.column=column;
		}
	}
	
	public static class ColumnSelectedMessage extends UserUIMessage {
		private int column;
		
		/**
		 * @return the column
		 */
		public int getColumn() {
			return column;
		}

		public ColumnSelectedMessage(String sender, int column) {
			super(sender, "ui_selected");
			this.column=column;
		}
	}

}
