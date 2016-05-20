package slides;

/**
 * Created by Evgeniy on 4/1/2016.
 */
public enum Rotation {
    NO_ROTATION {
        @Override
        public Rotation getNext() {
            return Rotation.ROTATE_LEFT;
        }

        @Override
        public double getRotationInRadians() {
            return 0;
        }
    },
    ROTATE_LEFT {
        @Override
        public Rotation getNext() {
            return Rotation.UPSIDE_DOWN;
        }

        @Override
        public double getRotationInRadians() {
            return Math.PI / 2;
        }
    },
    ROTATE_RIGHT {
        @Override
        public Rotation getNext() {

            return Rotation.NO_ROTATION;
        }

        @Override
        public double getRotationInRadians() {
            return (Math.PI * 3 / 2);
        }
    },
    UPSIDE_DOWN {
        @Override
        public Rotation getNext() {
            return Rotation.ROTATE_RIGHT;
        }

        @Override
        public double getRotationInRadians() {
            return Math.PI;
        }
    };

    public abstract Rotation getNext();

    public abstract double getRotationInRadians();
}
