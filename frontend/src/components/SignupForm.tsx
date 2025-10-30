import { useState } from 'react';
import { SignupRequest } from '../types/types';

interface SignupFormProps {
    onSignup: (data: SignupRequest) => void;
    onSwitchToLogin: () => void;
    error: string | null;
}

export default function SignupForm({ onSignup, onSwitchToLogin, error }: SignupFormProps) {
    const [formData, setFormData] = useState<SignupRequest>({
        email: '',
        password: '',
        fullName: '',
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSignup(formData);
    };

    return (
        <div className="auth-container">
            <div className="auth-card">
                <h2>Sign Up for Keep Dishes Going</h2>

                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="form-group">
                        <label>Full Name</label>
                        <input
                            type="text"
                            required
                            value={formData.fullName}
                            onChange={(e) => setFormData({ ...formData, fullName: e.target.value })}
                            placeholder="John Doe"
                        />
                    </div>

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
                            minLength={6}
                            value={formData.password}
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            placeholder="At least 6 characters"
                        />
                    </div>

                    <button type="submit" className="btn btn-primary btn-block">
                        Sign Up
                    </button>
                </form>

                <div className="auth-switch">
                    Already have an account?{' '}
                    <button onClick={onSwitchToLogin} className="link-btn">
                        Login here
                    </button>
                </div>
            </div>
        </div>
    );
}