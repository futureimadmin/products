import React from 'react';
import '../../styles/global.css';
import ananthaImage from '../../images/Anantha.JPG';

const Welcome = () => {
    const news = [
        {
            date: 'March 31, 2025',
            title: 'Future IM Launches Revolutionary Voice Commerce Platform',
            excerpt: 'Our new voice-enabled shopping experience is now live, making online shopping more accessible and intuitive than ever.'
        },
        {
            date: 'March 28, 2025',
            title: 'AI-Powered Product Recommendations',
            excerpt: 'Introducing personalized shopping experiences powered by advanced machine learning algorithms.'
        },
        {
            date: 'March 25, 2025',
            title: 'Future IM Expands Global Reach',
            excerpt: 'New partnerships established across Asia and Europe to enhance our international presence.'
        }
    ];

    const topStories = [
        {
            title: 'Voice Commerce: The Future of Shopping',
            excerpt: 'How voice technology is revolutionizing the e-commerce landscape and making shopping more accessible.'
        },
        {
            title: 'Sustainability in E-commerce',
            excerpt: 'Our commitment to eco-friendly packaging and sustainable business practices.'
        },
        {
            title: 'Customer Success Stories',
            excerpt: 'Real experiences from customers who have transformed their shopping experience with Future IM.'
        }
    ];

    return (
        <div className="welcome-container">
            <div className="welcome-grid">
                <div className="news-section">
                    <h2 className="section-title">Latest News</h2>
                    {news.map((item, index) => (
                        <div key={index} className="news-item">
                            <div className="news-date">{item.date}</div>
                            <div className="news-title">{item.title}</div>
                            <div className="news-excerpt">{item.excerpt}</div>
                        </div>
                    ))}
                </div>

                <div className="top-stories">
                    <h2 className="section-title">Top Stories</h2>
                    {topStories.map((story, index) => (
                        <div key={index} className="news-item">
                            <div className="news-title">{story.title}</div>
                            <div className="news-excerpt">{story.excerpt}</div>
                        </div>
                    ))}
                </div>

                <div className="from-desk">
                    <h2 className="section-title">From the Desk of Future IM</h2>
                    <p className="ceo-message">
                        "At Future IM, we're a team of Intelligent Machines serving for the needs of our customers."
                    </p>
                    <div className="news-excerpt">Founder & CEO, Future IM</div>
                </div>

                <div className="vision-section">
                    <h2 className="vision-title">Our Vision</h2>
                    <p className="vision-text">
                        Future IM envisions a world where technology enhances human experiences, not replaces them. 
                        We're pioneering voice commerce technology that makes online shopping as natural as having a conversation. 
                        Our commitment to innovation, accessibility, and customer satisfaction drives everything we do. 
                        Through cutting-edge AI and machine learning, we're creating personalized, intuitive shopping experiences 
                        that adapt to each customer's unique needs. Our mission is to make e-commerce more inclusive, 
                        efficient, and enjoyable for everyone, everywhere.
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Welcome;
