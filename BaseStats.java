public class BaseStats {
    int bstHp;
    int bstAtk;
    int bstSpaAtk;
    int bstDef;
    int bstSpaDef;
    int bstSpeed;

    public BaseStats(int bstHp, int bstAtk, int bstSpaAtk, int bstDef, int bstSpaDef,
            int bstSpeed) {
                this.bstHp = bstHp;
                this.bstAtk = bstAtk;
                this.bstSpaAtk = bstSpaAtk;
                this.bstDef = bstDef;
                this.bstSpaDef = bstSpaDef;
                this.bstSpeed = bstSpeed;
            }

    public BaseStats() {
                this.bstHp = 0;
                this.bstAtk = 0;
                this.bstSpaAtk = 0;
                this.bstDef = 0;
                this.bstSpaDef = 0;
                this.bstSpeed = 0;
    }        
}
