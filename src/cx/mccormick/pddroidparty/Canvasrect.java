package cx.mccormick.pddroidparty;

import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.Log;

public class Canvasrect extends Widget {
	RectF r = new RectF();
	
	private static int IEM_GUI_MAX_COLOR = 30;
	private static int iemgui_color_hex[] = {
		16579836, 10526880, 4210752, 16572640, 16572608,
		16579784, 14220504, 14220540, 14476540, 16308476,
		14737632, 8158332, 2105376, 16525352, 16559172,
		15263784, 1370132, 2684148, 3952892, 16003312,
		12369084, 6316128, 0, 9177096, 5779456,
		7874580, 2641940, 17488, 5256, 5767248
	};
	
	public Canvasrect(PdDroidPatchView app, String[] atomline) {
		super(app);
		
		x = Float.parseFloat(atomline[2]) / parent.patchwidth * screenwidth;
		y = Float.parseFloat(atomline[3]) / parent.patchheight * screenheight;
		w = Float.parseFloat(atomline[6]) / parent.patchwidth * screenwidth;
		h = Float.parseFloat(atomline[7]) / parent.patchheight * screenheight;
		
		receivename = atomline[9];
		setupreceive();
		
		// TODO: calculate and set fill colour
		r.left = x;
		r.top = y;
		r.right = (x + w);
		r.bottom = (y + h);
		
		int iemcolor = Integer.parseInt(atomline[15]);
		Log.e("ORIGINAL COLOR", "" + iemcolor);
		int color = 0;
		
		if(iemcolor < 0)
		{
			iemcolor = -1 - iemcolor;
			paint.setARGB(0xFF, (iemcolor & 0x3f000) >> 10, (iemcolor & 0xfc0) >> 4, iemcolor & 0x3f << 2);
		}
		else
		{
			iemcolor = iemgui_modulo_color(iemcolor);
			color = iemgui_color_hex[iemcolor] << 8 | 0xFF;
			paint.setColor(color);
		}
		
		Log.e("COLOR", "" + color);
		//paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		//paint.setStyle(Paint.Style.STROKE);
		//r.sort();
	}
	
	private int iemgui_modulo_color(int col) {
		while (col >= IEM_GUI_MAX_COLOR)
			col -= IEM_GUI_MAX_COLOR;
		while (col < 0)
			col += IEM_GUI_MAX_COLOR;
		return col;
	}
		
	public void draw(Canvas canvas) {
		//canvas.drawRect(r, paint);
		//canvas.drawRoundRect(r, 5, 5, paint);
		//canvas.drawLine(r.left, r.top, r.right, r.top, paint);
		
		//super.onDraw(canvas); 
		//paint.setAntiAlias(true); 
		//paint.setColor(android.graphics.Color.BLUE); 
		//paint.setStyle(Paint.Style.FILL_AND_STROKE);
		//paint.setStyle(Paint.Style.FILL);
		//canvas.drawRect(41, 124, 152, 73, paint);
		//Log.e("Canvasrect.draw()", r.left + ", " + r.top + ", " + r.right + ", " + r.bottom);
		canvas.drawRect(r.left, r.top, r.right, r.bottom, paint); 
		//canvas.drawCircle(110 + 50, 110 + 50, 50, paint); 
	}
	
	public void receiveMessage(String symbol, Object... args) {
		if (symbol.equals("pos")) {
			if (args.length == 2) {
				//Log.e("POS", args[0].toString() + ", " + args[1].toString());
				r.left = x = Float.parseFloat(args[0].toString()) / parent.patchwidth * screenwidth;
				r.top = y = Float.parseFloat(args[1].toString()) / parent.patchwidth * screenwidth;
				r.right = (x + w);
				r.bottom = (y + h);
				//r.sort();
			}
		}
	}
}