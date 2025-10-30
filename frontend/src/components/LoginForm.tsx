import { useState } from 'react';
import { LoginRequest } from '../types/types';

interface LoginFormProps {
    onLogin: (data: LoginRequest) => void;
    onSwitchToSignup: () => void;
    error: string | null;
}

export default function LoginForm({ onLogin, onSwitchToSignup, error }: LoginFormProps) {
    const [formData, setFormData] = useState<LoginRequest>({
        email: '',
        password: '',
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onLogin(formData);
    };

    return (
        <div className="auth-container">
            <div className="auth-card">
                <h2>Login to Keep Dishes Going</h2>

                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            required
                            value={formData.email}
                            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                            placeholder="your@email.com"
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            required
                            value={formData.password}
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            placeholder="Enter your password"
                        />
                    </div>

                    <button type="submit" className="btn btn-primary btn-block">
                        Login
                    </button>
                </form>

                <div className="auth-switch">
                    Don't have an account?{' '}
                    <button onClick={onSwitchToSignup} className="link-btn">
                        Sign up here
                    </button>
                </div>
            </div>
        </div>
    );
}