# 使い方

```java
import net.minecraft.client.MinecraftClient;

public void onEnable() {
    MinecraftClient.getInstance().setScreenAndRender(new ClickGuiMain());
    this.disable();
}
```

Modを有効にした際にScreenを表示させるのが一番シンプルで簡単です。

# How to use

```java
import net.minecraft.client.MinecraftClient;

public void onEnable() {
    MinecraftClient.getInstance().setScreenAndRender(new ClickGuiMain());
    this.disable();
}
```

The simplest and easiest way is to display the screen when the mod is enabled.