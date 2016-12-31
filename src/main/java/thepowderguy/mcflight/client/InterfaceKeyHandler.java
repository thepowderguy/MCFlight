package thepowderguy.mcflight.client;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.VboRenderList;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import thepowderguy.mcflight.common.entity.CameraView;
import thepowderguy.mcflight.common.entity.EntityAirplane;
import thepowderguy.mcflight.common.entity.EntityAirplaneCamera;
import thepowderguy.mcflight.common.entity.biplane.RenderBiplane;

public class InterfaceKeyHandler {
	public boolean debug_toggled = false;
	public boolean vectordrawing_toggled = false;
	public boolean hud_toggled = true;
	public int camera_mode = 0;
	
	public KeyBinding toggleDebug;
	public KeyBinding toggleVector;
	public KeyBinding toggleHud;
	public KeyBinding changeCamera;
	public KeyBinding zoom_in;
	public KeyBinding zoom_out;
	public KeyBinding alieron_cw	;
	public KeyBinding alieron_ccw  ;
	public KeyBinding elevator_up  ;
	public KeyBinding elevator_down;
	public KeyBinding rudder_left  ;
	public KeyBinding rudder_right ;
	public KeyBinding throttle_up  ;
	public KeyBinding throttle_down;
	public KeyBinding brake;
	public KeyBinding look_around  ;


	public void init() {
		toggleDebug   = new KeyBinding("key.airplanedebug", Keyboard.KEY_F9, "key.categories.mcflight");
		toggleVector  = new KeyBinding("key.airplaneshowvectors", Keyboard.KEY_F10, "key.categories.mcflight");
		toggleHud  	= new KeyBinding("key.airplaneshowhud", Keyboard.KEY_F8, "key.categories.mcflight");
		zoom_in  = new KeyBinding("key.zoomin", Keyboard.KEY_EQUALS, "key.categories.mcflight");
		zoom_out  	= new KeyBinding("key.zoomout", Keyboard.KEY_MINUS, "key.categories.mcflight");
		alieron_cw	  = new KeyBinding("key.alieron_cw", Keyboard.KEY_LEFT, "key.categories.mcflight");
		alieron_ccw   = new KeyBinding("key.alieron_ccw", Keyboard.KEY_RIGHT, "key.categories.mcflight");
		elevator_up   = new KeyBinding("key.elevator_up", Keyboard.KEY_UP, "key.categories.mcflight");
		elevator_down = new KeyBinding("key.elevator_down", Keyboard.KEY_DOWN, "key.categories.mcflight");
		rudder_left   = new KeyBinding("key.rudder_left", Keyboard.KEY_J, "key.categories.mcflight");
		rudder_right  = new KeyBinding("key.rudder_right", Keyboard.KEY_L, "key.categories.mcflight");
		throttle_up   = new KeyBinding("key.throttle_up", Keyboard.KEY_I, "key.categories.mcflight");
		throttle_down = new KeyBinding("key.throttle_down", Keyboard.KEY_K, "key.categories.mcflight");
		brake         = new KeyBinding("key.brake", Keyboard.KEY_Z, "key.categories.mcflight");
		look_around   = new KeyBinding("key.look_around", Keyboard.KEY_SPACE, "key.categories.mcflight");
		changeCamera   = new KeyBinding("key.change_camera", Keyboard.KEY_F6, "key.categories.mcflight");
		ClientRegistry.registerKeyBinding(toggleVector);
		ClientRegistry.registerKeyBinding(toggleDebug);
		ClientRegistry.registerKeyBinding(toggleHud);
		ClientRegistry.registerKeyBinding(zoom_in   );
		ClientRegistry.registerKeyBinding(zoom_out	);   
		ClientRegistry.registerKeyBinding(alieron_ccw   );
		ClientRegistry.registerKeyBinding(alieron_cw	);   
		ClientRegistry.registerKeyBinding(elevator_up   );
		ClientRegistry.registerKeyBinding(elevator_down );
		ClientRegistry.registerKeyBinding(rudder_left   );
		ClientRegistry.registerKeyBinding(rudder_right  );
		ClientRegistry.registerKeyBinding(throttle_up   );
		ClientRegistry.registerKeyBinding(throttle_down );
		ClientRegistry.registerKeyBinding(brake         );
		ClientRegistry.registerKeyBinding(look_around   );
		ClientRegistry.registerKeyBinding(changeCamera   );

	}
	public InterfaceKeyHandler() {

	}
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (toggleDebug.isPressed()) {
			debug_toggled = !debug_toggled;

/*		        if (this.world != null)
		        {
		            if (this.renderDispatcher == null)
		            {
		                this.renderDispatcher = new ChunkRenderDispatcher();
		            }

		            this.displayListEntitiesDirty = true;
		            Blocks.LEAVES.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
		            Blocks.LEAVES2.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
		            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
		            boolean flag = this.vboEnabled;
		            this.vboEnabled = OpenGlHelper.useVbo();

		            if (flag && !this.vboEnabled)
		            {
		                this.renderContainer = new RenderList();
		                this.renderChunkFactory = new ListChunkFactory();
		            }
		            else if (!flag && this.vboEnabled)
		            {
		                this.renderContainer = new VboRenderList();
		                this.renderChunkFactory = new VboChunkFactory();
		            }

		            if (flag != this.vboEnabled)
		            {
		                this.generateStars();
		                this.generateSky();
		                this.generateSky2();
		            }

		            if (this.viewFrustum != null)
		            {
		                this.viewFrustum.deleteGlResources();
		            }

		            this.stopChunkUpdates();

		            synchronized (this.setTileEntities)
		            {
		                this.setTileEntities.clear();
		            }

		            this.viewFrustum = new ViewFrustum(this.world, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);

		            if (this.world != null)
		            {
		                Entity entity = this.mc.getRenderViewEntity();

		                if (entity != null)
		                {
		                    this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
		                }
		            }

		            this.renderEntitiesStartupCounter = 2;
		        }*/
		}
		if (toggleVector.isPressed()) {
			vectordrawing_toggled = !vectordrawing_toggled;
		}
		if (toggleHud.isPressed()) {
			hud_toggled = !hud_toggled;
		}
		if (changeCamera.isPressed()) {
			camera_mode = (camera_mode+1)%6;
			Minecraft.getMinecraft().ingameGUI.setOverlayMessage(I18n.format(EntityAirplaneCamera.views[camera_mode].name), false);
		}
	}
}
