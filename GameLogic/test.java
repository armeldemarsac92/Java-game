//    protected void loadAnimationFrames() {
//         animationFrames = new ArrayList<>();

//         try {
//             for (int i = 1; i <= this.numberOfFrames; i++) { 
//                 Image img = ImageIO.read(new File(this.coreFilePath + i + ".png"));
//                 Image scaledImg = img.getScaledInstance(this.sizeX, this.sizeY, Image.SCALE_SMOOTH); 
//                 this.animationFrames.add(new ImageIcon(scaledImg));
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
    
//     protected void reloadAnimationFramesAsync() {
//         new SwingWorker<List<ImageIcon>, Void>() {
//             @Override
//             protected List<ImageIcon> doInBackground() throws Exception {
//                 List<ImageIcon> newFrames = new ArrayList<>();
//                 loadAnimationFrames();
//                 // Load new frames (similar to loadAnimationFrames method)
//                 // Ensure this does not modify any Swing components directly
//                 return newFrames;
//             }
    
//             @Override
//             protected void done() {
//                 try {
//                     // Update animation frames on the EDT
//                     List<ImageIcon> newFrames = get();
//                     updateAnimationFrames(newFrames);
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             }
//         }.execute();
//     }
    
//     private void updateAnimationFrames(List<ImageIcon> newFrames) {
//         // Stop current animation if running
//         if (this.animationTimer != null) {
//             this.animationTimer.stop();
//         }
    
//         // Update frames
//         this.animationFrames = newFrames;
    
//         // Reset animation state
//         this.currentFrame = 0;
//         if (((AMob)this).isAlive()){
//             // Restart animation
//             startAnimation();
//         }
//     }
    

    

//     protected void startAnimation() {
//         this.animationTimer = new Timer(50, e -> updateAnimation());
//         this.animationTimer.start();
//     }

//     public void updateAnimation() {
//         if (!animationFrames.isEmpty()) {
//             this.currentFrame = (this.currentFrame + 1) % animationFrames.size();
//             this.unitLabel.setIcon(animationFrames.get(this.currentFrame));
//         }
//     }